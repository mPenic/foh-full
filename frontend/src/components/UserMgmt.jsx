import React, { useState, useEffect } from "react";
import {
  Box,
  Heading,
  Text,
  Table,
  Thead,
  Tbody,
  Tr,
  Th,
  Td,
  Button,
  Input,
  Select,
  FormControl,
  FormLabel,
  VStack,
  Spinner,
  useToast,
  Flex,
  IconButton,
  TableContainer,
} from "@chakra-ui/react";
import { FaTrash } from "react-icons/fa";
import { useAuth } from "./AuthContext";
import Navigation from "./Navigation"; // ✅ add navigation

export default function UserMgmt() {
  const { userDetails, isLoading } = useAuth();
  const toast = useToast();

  const [profile, setProfile] = useState(null);
  const [roles, setRoles] = useState([]);
  const [users, setUsers] = useState([]);
  const [newUser, setNewUser] = useState({
    username: "",
    password: "",
    roleId: "",
  });

  // Fetch profile
  useEffect(() => {
    if (!isLoading) {
      fetch(`/api/users/${userDetails.userId}`, { credentials: "include" })
        .then((r) => r.json())
        .then(setProfile)
        .catch(console.error);
    }
  }, [isLoading, userDetails.userId]);

  // Fetch roles
  useEffect(() => {
    fetch("/api/roles", { credentials: "include" })
      .then((r) => r.json())
      .then(setRoles)
      .catch(console.error);
  }, []);

  const isAdmin = profile?.roleName === "Admin";

  // Fetch company users if admin
  useEffect(() => {
    if (isAdmin && profile?.companyId) {
      fetch(`/api/users?companyId=${profile.companyId}`, {
        credentials: "include",
      })
        .then((r) => r.json())
        .then(setUsers)
        .catch(console.error);
    }
  }, [isAdmin, profile?.companyId]);

  async function handleCreate() {
    if (!newUser.username || !newUser.password || !newUser.roleId) {
      return toast({ title: "All fields required", status: "error" });
    }
    try {
      const res = await fetch("/api/users", {
        method: "POST",
        credentials: "include",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(newUser),
      });
      if (!res.ok) throw new Error();
      const created = await res.json();
      toast({ title: "User created!", status: "success" });
      setUsers((u) => [...u, created]);
      setNewUser({ username: "", password: "", roleId: "" });
    } catch {
      toast({ title: "Failed to create", status: "error" });
    }
  }

  async function handleDelete(id) {
    if (!window.confirm("Delete this user?")) return;
    try {
      const res = await fetch(`/api/users/${id}`, {
        method: "DELETE",
        credentials: "include",
      });
      if (!res.ok) throw new Error();
      setUsers((u) => u.filter((x) => x.userId !== id));
      toast({ title: "Deleted", status: "info" });
    } catch {
      toast({ title: "Delete failed", status: "error" });
    }
  }

  if (isLoading || !profile) {
    return (
      <Flex minH="100vh" bg="black" align="center" justify="center">
        <Spinner size="xl" color="white" />
      </Flex>
    );
  }

  return (
    <Flex direction="column" h="100vh" bg="black" color="white">
      <Navigation /> {/* ✅ Navigation at top */}
      <Box
        flex="1"
        overflowY="auto"
        p={{ base: 4, md: 6 }}
        pt={{ base: 16, md: 6 }} // ⬅ extra top padding only on small screens
      >
        {/* Profile card */}
        <Heading size="lg" mb={2} ml="60px">
          {" "}
          {/* ⬅ Added ml so it's not behind nav icon */}
          User Profile
        </Heading>
        <Box
          bg="gray.900"
          borderRadius="lg"
          p={4}
          border="1px solid"
          borderColor="gray.700"
          mb={6}
        >
          <Text>
            <b>Username:</b> {profile.username}
          </Text>
          <Text>
            <b>Company:</b> {profile.companyName}
          </Text>
          <Text>
            <b>Role:</b> {profile.roleName}
          </Text>
        </Box>

        {isAdmin && (
          <>
            {/* Users table */}
            <Heading size="md" mb={3}>
              Manage Company Users
            </Heading>
            <TableContainer
              bg="gray.900"
              borderRadius="lg"
              border="1px solid"
              borderColor="gray.700"
              mb={6}
            >
              <Table variant="simple" size="md">
                <Thead bg="gray.800">
                  <Tr>
                    <Th color="gray.200">Username</Th>
                    <Th color="gray.200">Role</Th>
                    <Th textAlign="right" color="gray.200">
                      Actions
                    </Th>
                  </Tr>
                </Thead>
                <Tbody>
                  {users.map((u) => (
                    <Tr
                      key={u.userId}
                      _hover={{ bg: "gray.800" }}
                      borderBottom="1px solid"
                      borderColor="gray.800"
                    >
                      <Td color="gray.100">{u.username}</Td>
                      <Td color="gray.100">{u.roleName}</Td>
                      <Td textAlign="right">
                        <IconButton
                          icon={<FaTrash />}
                          size="sm"
                          bg="red.500"
                          _hover={{ bg: "red.600" }}
                          color="white"
                          onClick={() => handleDelete(u.userId)}
                          aria-label="Delete user"
                        />
                      </Td>
                    </Tr>
                  ))}
                  {users.length === 0 && (
                    <Tr>
                      <Td
                        colSpan={3}
                        color="gray.400"
                        textAlign="center"
                        py={8}
                      >
                        No users yet
                      </Td>
                    </Tr>
                  )}
                </Tbody>
              </Table>
            </TableContainer>

            {/* Create new user form */}
            <Box
              bg="gray.900"
              borderRadius="lg"
              border="1px solid"
              borderColor="gray.700"
              p={4}
            >
              <Heading size="sm" mb={3}>
                Create New User
              </Heading>
              <VStack spacing={3} align="stretch">
                <FormControl>
                  <FormLabel color="gray.300">Username</FormLabel>
                  <Input
                    bg="gray.700"
                    borderColor="gray.600"
                    _hover={{ borderColor: "gray.500" }}
                    _focus={{
                      borderColor: "blue.400",
                      boxShadow: "0 0 0 1px #63b3ed",
                    }}
                    color="white"
                    placeholder="Enter username"
                    value={newUser.username}
                    onChange={(e) =>
                      setNewUser((n) => ({ ...n, username: e.target.value }))
                    }
                  />
                </FormControl>

                <FormControl>
                  <FormLabel color="gray.300">Password</FormLabel>
                  <Input
                    type="password"
                    bg="gray.700"
                    borderColor="gray.600"
                    _hover={{ borderColor: "gray.500" }}
                    _focus={{
                      borderColor: "blue.400",
                      boxShadow: "0 0 0 1px #63b3ed",
                    }}
                    color="white"
                    placeholder="Enter password"
                    value={newUser.password}
                    onChange={(e) =>
                      setNewUser((n) => ({ ...n, password: e.target.value }))
                    }
                  />
                </FormControl>

                <FormControl>
                  <FormLabel color="gray.300">Role</FormLabel>
                  <Select
                    placeholder="Select role"
                    bg="gray.700"
                    color="white"
                    borderColor="gray.600"
                    _hover={{ borderColor: "gray.500" }}
                    _focus={{
                      borderColor: "blue.400",
                      boxShadow: "0 0 0 1px #63b3ed",
                    }}
                    value={newUser.roleId}
                    onChange={(e) =>
                      setNewUser((n) => ({
                        ...n,
                        roleId: Number(e.target.value),
                      }))
                    }
                  >
                    {roles.map((r) => (
                      <option
                        key={r.roleId}
                        value={r.roleId}
                        style={{ background: "#2D3748", color: "white" }}
                      >
                        {r.roleName}
                      </option>
                    ))}
                  </Select>
                </FormControl>

                <Flex justify="flex-end" pt={2}>
                  <Button
                    onClick={handleCreate}
                    color="white"
                    bgGradient="linear(to-r, #ff0080, #00bfff)"
                    _hover={{ opacity: 0.9 }}
                  >
                    Create
                  </Button>
                </Flex>
              </VStack>
            </Box>
          </>
        )}
      </Box>
    </Flex>
  );
}
