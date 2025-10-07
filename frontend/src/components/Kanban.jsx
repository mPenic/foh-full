import React, { useState, useEffect, useMemo } from "react";
import webSocketService from "../websocket";
import { useAuth } from "./AuthContext";
import {
  Box,
  Button,
  Flex,
  Text,
  IconButton,
  Input,
  Select,
  useToast,
  Modal,
  ModalOverlay,
  ModalContent,
  ModalHeader,
  ModalFooter,
  ModalBody,
  ModalCloseButton,
  useDisclosure,
  RadioGroup,
  Radio,
  Stack,
  CheckboxGroup,
  Checkbox,
  Spinner,
  Menu,
  MenuButton,
  MenuList,
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  useBreakpointValue,
} from "@chakra-ui/react";
import { FaPlus, FaTrash } from "react-icons/fa";
import {
  ChevronDownIcon,
  ArrowBackIcon,
  ArrowForwardIcon,
} from "@chakra-ui/icons";
import Navigation from "./Navigation";
import { DragDropContext, Droppable, Draggable } from "react-beautiful-dnd";

export default function KanbanBoard() {
  const { userDetails, isLoading } = useAuth();
  const toast = useToast();
  const { isOpen, onOpen, onClose } = useDisclosure();

  // state
  const [allTasks, setAllTasks] = useState([]);
  const [statuses, setStatuses] = useState([]);
  const [users, setUsers] = useState([]);
  const [roles, setRoles] = useState([]);
  const [newTask, setNewTask] = useState({
    title: "",
    description: "",
    assignedToUserId: null,
    roleIds: [],
    priority: 3,
  });
  const [mode, setMode] = useState("assign");
  const [wsConnected, setWsConnected] = useState(false);
  const [filterStatus, setFilterStatus] = useState("Pending");

  // fetch dicts & tasks
  useEffect(() => {
    if (isLoading) return;
    fetch("/api/task-statuses", { credentials: "include" })
      .then((r) => r.json())
      .then(setStatuses)
      .catch(console.error);

    fetch(`/api/users?companyId=${userDetails.companyId}`, {
      credentials: "include",
    })
      .then((r) => r.json())
      .then(setUsers)
      .catch(console.error);

    fetch("/api/roles", { credentials: "include" })
      .then((r) => r.json())
      .then(setRoles)
      .catch(console.error);

    fetchTasks();
  }, [isLoading, userDetails.companyId]);

  useEffect(() => {
    if (
      isLoading ||
      !userDetails.companyId ||
      !userDetails.roleId ||
      wsConnected
    )
      return;

    webSocketService.connect(() => setWsConnected(true));
    webSocketService.subscribeToTasks(
      userDetails.companyId,
      "kanban",
      userDetails.roleId,
      (taskOrDeleted) => {
        if (typeof taskOrDeleted === "number") {
          setAllTasks((ts) => ts.filter((t) => t.taskId !== taskOrDeleted));
        } else {
          setAllTasks((prev) => {
            const without = prev.filter(
              (t) => t.taskId !== taskOrDeleted.taskId
            );
            return [...without, taskOrDeleted];
          });
        }
      }
    );
    return () => webSocketService.unsubscribeAll();
  }, [isLoading, userDetails.companyId, userDetails.roleId, wsConnected]);

  async function fetchTasks() {
    try {
      const res = await fetch("/api/tasks", { credentials: "include" });
      if (res.ok) setAllTasks(await res.json());
    } catch (e) {
      console.error(e);
    }
  }

  // statuses by name (avoid optional chaining)
  const openStatus = useMemo(
    () => statuses.find((s) => s.taskStatusName === "Open"),
    [statuses]
  );
  const pendingStatus = useMemo(
    () => statuses.find((s) => s.taskStatusName === "Pending"),
    [statuses]
  );
  const inProgressStatus = useMemo(
    () => statuses.find((s) => s.taskStatusName === "In Progress"),
    [statuses]
  );
  const completedStatus = useMemo(
    () => statuses.find((s) => s.taskStatusName === "Completed"),
    [statuses]
  );

  // ids (plain JS, no ?. )
  const pendingId = pendingStatus ? pendingStatus.statusId : undefined;
  const inProgressId = inProgressStatus ? inProgressStatus.statusId : undefined;
  const completedId = completedStatus ? completedStatus.statusId : undefined;

  // admin?
  const adminRole = roles.find((r) => r.roleName === "Admin");
  const isAdmin = adminRole && userDetails.roleId === adminRole.roleId;

  // filter visible tasks
  const visibleTasks = useMemo(() => {
    if (isAdmin) return allTasks;
    const openId = openStatus ? openStatus.statusId : -1;
    return allTasks.filter(
      (t) =>
        (t.status.statusId === openId &&
          Array.isArray(t.roleIds) &&
          t.roleIds.includes(userDetails.roleId)) ||
        (t.assignedTo && t.assignedTo.userId === userDetails.userId)
    );
  }, [allTasks, isAdmin, openStatus, userDetails]);

  // sort
  const sortByPriority = (arr) =>
    [...arr].sort((a, b) => a.priority - b.priority);

  // mobile filtering
  const isMobile = useBreakpointValue({ base: true, md: false });
  const currentFilterStatusId =
    filterStatus === "Pending"
      ? pendingId
      : filterStatus === "In Progress"
      ? inProgressId
      : completedId;

  const mobileTasks = visibleTasks.filter(
    (t) => t.status && t.status.statusId === currentFilterStatusId
  );

  // helpers for arrows
  function nextStatusId(sid) {
    if (sid === pendingId) return inProgressId;
    if (sid === inProgressId) return completedId;
    return null;
  }
  function prevStatusId(sid) {
    if (sid === completedId) return inProgressId;
    if (sid === inProgressId) return pendingId;
    return null;
  }
  function moveRight(t) {
    const cur = t && t.status ? t.status.statusId : undefined;
    const target = nextStatusId(cur);
    if (target) updateStatus(t.taskId, target);
  }
  function moveLeft(t) {
    const cur = t && t.status ? t.status.statusId : undefined;
    const target = prevStatusId(cur);
    if (target) updateStatus(t.taskId, target);
  }

  // create
  async function createTask() {
    if (!newTask.title.trim())
      return toast({ title: "Title required", status: "error" });

    const payload = {
      title: newTask.title,
      description: newTask.description,
      priority: newTask.priority,
      assignedToUserId: mode === "assign" ? newTask.assignedToUserId : null,
      roleIds:
        mode === "assign"
          ? []
          : Array.isArray(newTask.roleIds)
          ? newTask.roleIds
          : [],
    };

    try {
      const res = await fetch("/api/tasks", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });
      if (res.ok) {
        toast({ title: "Task created!", status: "success" });
        onClose();
        setNewTask({
          title: "",
          description: "",
          priority: 3,
          assignedToUserId: null,
          roleIds: [],
        });
        fetchTasks();
      } else {
        toast({ title: "Creation failed", status: "error" });
      }
    } catch (e) {
      console.error(e);
      toast({ title: "Creation error", status: "error" });
    }
  }

  // update
  async function updateStatus(taskId, statusId) {
    const myId = userDetails.userId;

    const res = await fetch(`/api/tasks/${taskId}`, {
      method: "PUT",
      credentials: "include",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ statusId, assignedToUserId: myId }),
    });

    if (res.ok) {
      const updated = await res.json();
      setAllTasks((ts) =>
        ts.map((t) => (t.taskId === updated.taskId ? updated : t))
      );

      // ✅ Show toast based on status name
      const movedTo = statuses.find(
        (s) => s.statusId === updated.status.statusId
      )?.taskStatusName;
      toast({
        title: `Task moved to ${movedTo} column!`,
        status: "success",
        duration: 2000,
        isClosable: true,
      });
    } else {
      toast({
        title: "Failed to move task",
        status: "error",
        duration: 2000,
        isClosable: true,
      });
    }
  }

  function handleDragEnd(result) {
    if (!result.destination) return;
    updateStatus(result.draggableId, Number(result.destination.droppableId));
  }

  async function deleteTask(id) {
    await fetch(`/api/tasks/${id}`, {
      method: "DELETE",
      credentials: "include",
    });
    fetchTasks();
  }

  if (isLoading || statuses.length === 0) {
    return (
      <Flex h="100vh" align="center" justify="center">
        <Spinner size="xl" />
      </Flex>
    );
  }

  // priority → shade
  const priorityShade = (p) => {
    const shades = ["gray.700", "gray.600", "gray.500", "gray.400", "gray.300"];
    return shades[p - 1] || "gray.700";
  };

  return (
    <Flex direction="column" h="100vh" bg="black" color="white">
      <Navigation />

      {/* HEADER */}
      <Flex
        p="4"
        align="center"
        justify="space-between"
        borderBottom="2px solid white"
      >
        <Text fontSize="xl" fontWeight="bold" pl="16">
          Kanban Board
        </Text>
        {isAdmin && (
          <IconButton
            icon={<FaPlus />}
            onClick={onOpen}
            bgGradient="linear(to-r, #ff0080, #00bfff)"
          />
        )}
      </Flex>

      {/* OPEN-TASKS DROPDOWN */}
      <Flex p="4" bg="gray.900">
        <Menu>
          <MenuButton
            as={Button}
            rightIcon={<ChevronDownIcon />}
            bg="gray.700"
            color="white"
            flex="1"
            whiteSpace="nowrap"
            overflow="hidden"
            textOverflow="ellipsis"
          >
            Assign open…
          </MenuButton>
          <MenuList
            bg="gray.800"
            color="white"
            w="100vw"
            maxW="100vw"
            borderRadius="md"
            ml="-4"
            px="2"
          >
            <Table variant="simple" size="sm" layout="fixed">
              <Thead>
                <Tr>
                  <Th w="60px">Priority</Th>
                  <Th w="100px">Title</Th>
                  <Th>Description</Th>
                  <Th w="80px" textAlign="right">
                    Action
                  </Th>
                </Tr>
              </Thead>
              <Tbody>
                {openStatus &&
                  sortByPriority(
                    visibleTasks.filter(
                      (t) => t.status.statusId === openStatus.statusId
                    )
                  ).map((t) => (
                    <Tr key={t.taskId} bg={priorityShade(t.priority)}>
                      <Td>{t.priority}</Td>
                      <Td whiteSpace="normal" wordBreak="break-word">
                        {t.title}
                      </Td>
                      <Td whiteSpace="normal" wordBreak="break-word">
                        {t.description}
                      </Td>
                      <Td
                        whiteSpace="normal"
                        wordBreak="break-word"
                        maxW="80px"
                        px="1"
                      >
                        <Box w="100%" overflow="hidden">
                          <Button
                            size="sm"
                            width="100%"
                            fontSize="xs"
                            whiteSpace="normal"
                            onClick={() => updateStatus(t.taskId, pendingId)}
                          >
                            Assign me
                          </Button>
                        </Box>
                      </Td>
                    </Tr>
                  ))}
                {!visibleTasks.some(
                  (t) => openStatus && t.status.statusId === openStatus.statusId
                ) && (
                  <Tr>
                    <Td colSpan={4} textAlign="center" color="gray.500">
                      No tasks available
                    </Td>
                  </Tr>
                )}
              </Tbody>
            </Table>
          </MenuList>
        </Menu>
      </Flex>

      {/* BOARD */}
      <Box flex="1" overflowY="auto" pb={isMobile ? "60px" : 0}>
        {isMobile ? (
          // MOBILE VIEW
          <Box p="4">
            {sortByPriority(mobileTasks).map((t) => {
              const curId = t && t.status ? t.status.statusId : undefined;
              const canLeft = !!prevStatusId(curId);
              const canRight = !!nextStatusId(curId);
              return (
                <Box
                  key={t.taskId}
                  bg={priorityShade(t.priority)}
                  borderRadius="md"
                  p="4"
                  mb="3"
                >
                  <Text fontWeight="bold">
                    {t.title}{" "}
                    {isAdmin && t.assignedTo && (
                      <Text as="span" fontWeight="normal" fontSize="sm">
                        (Assigned to: {t.assignedTo.username})
                      </Text>
                    )}
                  </Text>
                  <Text>{t.description}</Text>
                  <Flex mt="2" align="center" justify="space-between" w="100%">
                    <IconButton
                      aria-label="Move left"
                      icon={<ArrowBackIcon />}
                      size="sm"
                      onClick={() => moveLeft(t)}
                      isDisabled={!canLeft}
                    />

                    <IconButton
                      size="sm"
                      icon={<FaTrash />}
                      colorScheme="red"
                      onClick={() => deleteTask(t.taskId)}
                      mx="auto"
                    />

                    <IconButton
                      aria-label="Move right"
                      icon={<ArrowForwardIcon />}
                      size="sm"
                      onClick={() => moveRight(t)}
                      isDisabled={!canRight}
                    />
                  </Flex>
                </Box>
              );
            })}
            {mobileTasks.length === 0 && (
              <Text textAlign="center" mt="8" color="gray.500">
                No {filterStatus.toLowerCase()} tasks
              </Text>
            )}
          </Box>
        ) : (
          // DESKTOP VIEW
          <DragDropContext onDragEnd={handleDragEnd}>
            <Flex>
              {statuses
                .filter((s) => s.taskStatusName !== "Open")
                .map((stat) => (
                  <Droppable
                    key={stat.statusId}
                    droppableId={`${stat.statusId}`}
                    isDropDisabled={false}
                    isCombineEnabled={false}
                    ignoreContainerClipping={false}
                  >
                    {(prov) => (
                      <Box
                        ref={prov.innerRef}
                        {...prov.droppableProps}
                        flex="1"
                        m="2"
                        p="2"
                        bg="gray.900"
                        height="100%"
                        display="flex"
                        flexDirection="column"
                      >
                        <Text mb="2" fontWeight="bold">
                          {stat.taskStatusName}
                        </Text>
                        {sortByPriority(
                          visibleTasks.filter(
                            (t) => t.status.statusId === stat.statusId
                          )
                        ).map((t, idx) => {
                          const curId =
                            t && t.status ? t.status.statusId : undefined;
                          const canLeft = !!prevStatusId(curId);
                          const canRight = !!nextStatusId(curId);
                          return (
                            <Draggable
                              key={t.taskId}
                              draggableId={`${t.taskId}`}
                              index={idx}
                            >
                              {(p) => (
                                <Box
                                  ref={p.innerRef}
                                  {...p.draggableProps}
                                  {...p.dragHandleProps}
                                  bg={priorityShade(t.priority)}
                                  borderRadius="md"
                                  p="4"
                                  m="2"
                                >
                                  <Text fontWeight="bold">
                                    {t.title}{" "}
                                    {isAdmin && t.assignedTo && (
                                      <Text
                                        as="span"
                                        fontWeight="normal"
                                        fontSize="sm"
                                      >
                                        (Assigned to: {t.assignedTo.username})
                                      </Text>
                                    )}
                                  </Text>
                                  <Text>{t.description}</Text>
                                  <Flex mt="2" gap="2" justify="flex-end">
                                    <IconButton
                                      aria-label="Move left"
                                      icon={<ArrowBackIcon />}
                                      size="sm"
                                      onClick={() => moveLeft(t)}
                                      isDisabled={!canLeft}
                                    />
                                    <IconButton
                                      aria-label="Move right"
                                      icon={<ArrowForwardIcon />}
                                      size="sm"
                                      onClick={() => moveRight(t)}
                                      isDisabled={!canRight}
                                    />
                                    {isAdmin && (
                                      <IconButton
                                        icon={<FaTrash />}
                                        colorScheme="red"
                                        size="sm"
                                        onClick={() => deleteTask(t.taskId)}
                                      />
                                    )}
                                  </Flex>
                                </Box>
                              )}
                            </Draggable>
                          );
                        })}
                        {prov.placeholder}
                      </Box>
                    )}
                  </Droppable>
                ))}
            </Flex>
          </DragDropContext>
        )}
      </Box>

      {/* MOBILE NAV */}
      {isMobile && (
        <Flex
          pos="fixed"
          bottom="0"
          width="100%"
          bg="gray.900"
          justify="space-around"
          p="2"
        >
          {["Pending", "In Progress", "Completed"].map((label) => (
            <Button
              key={label}
              size="sm"
              variant={filterStatus === label ? "solid" : "outline"}
              colorScheme="pink"
              color="white"
              borderColor="gray.400"
              _hover={{ bg: "gray.700" }}
              onClick={() => setFilterStatus(label)}
            >
              {label}
            </Button>
          ))}
        </Flex>
      )}

      {/* CREATE TASK MODAL */}
      {isAdmin && (
        <Modal isOpen={isOpen} onClose={onClose} size="lg">
          <ModalOverlay />
          <ModalContent bg="gray.800" color="white">
            <ModalHeader>Create Task</ModalHeader>
            <ModalCloseButton />
            <ModalBody>
              <Input
                placeholder="Title"
                mb="2"
                value={newTask.title}
                onChange={(e) =>
                  setNewTask({ ...newTask, title: e.target.value })
                }
              />
              <Input
                placeholder="Description"
                mb="2"
                value={newTask.description}
                onChange={(e) =>
                  setNewTask({ ...newTask, description: e.target.value })
                }
              />
              <Select
                mb="4"
                value={newTask.priority}
                onChange={(e) =>
                  setNewTask({ ...newTask, priority: Number(e.target.value) })
                }
                bg="gray.700"
                color="white"
                borderColor="gray.500"
                _hover={{ borderColor: "gray.400" }}
                _focus={{
                  borderColor: "blue.400",
                  boxShadow: "0 0 0 1px blue.400",
                }}
              >
                {[1, 2, 3, 4, 5].map((n) => (
                  <option
                    key={n}
                    value={n}
                    style={{ backgroundColor: "#2D3748", color: "white" }}
                  >
                    Priority {n}
                  </option>
                ))}
              </Select>

              <RadioGroup onChange={setMode} value={mode} mb="4">
                <Stack direction="row">
                  <Radio value="assign">Assign to User</Radio>
                  <Radio value="open">Open for Roles</Radio>
                </Stack>
              </RadioGroup>

              {mode === "assign" ? (
                <Select
                  placeholder="Select user…"
                  value={newTask.assignedToUserId || ""}
                  onChange={(e) =>
                    setNewTask({
                      ...newTask,
                      assignedToUserId: Number(e.target.value),
                    })
                  }
                  bg="gray.700"
                  color="white"
                  borderColor="gray.500"
                  _hover={{ borderColor: "gray.400" }}
                  _focus={{
                    borderColor: "blue.400",
                    boxShadow: "0 0 0 1px blue.400",
                  }}
                >
                  {users.map((u) => (
                    <option
                      key={u.userId}
                      value={u.userId}
                      style={{ backgroundColor: "#2D3748", color: "white" }}
                    >
                      {u.username}
                    </option>
                  ))}
                </Select>
              ) : (
                <CheckboxGroup
                  colorScheme="pink"
                  value={(newTask.roleIds || []).map(String)}
                  onChange={(vals) =>
                    setNewTask({ ...newTask, roleIds: vals.map(Number) })
                  }
                >
                  <Stack spacing={2}>
                    {roles.map((r) => (
                      <Checkbox key={r.roleId} value={String(r.roleId)}>
                        {r.roleName}
                      </Checkbox>
                    ))}
                  </Stack>
                </CheckboxGroup>
              )}
            </ModalBody>
            <ModalFooter>
              <Button onClick={createTask} colorScheme="blue">
                Create
              </Button>
            </ModalFooter>
          </ModalContent>
        </Modal>
      )}
    </Flex>
  );
}
