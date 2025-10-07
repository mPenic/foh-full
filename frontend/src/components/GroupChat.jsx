import React, { useState } from "react";
import {
  Box,
  Flex,
  Text,
  Input,
  Button,
  Select,
} from "@chakra-ui/react";
import Navigation from "./Navigation"; // Import the Navigation component

// Dummy groups array
const groups = [
  { id: 1, name: "General Chat" },
  { id: 2, name: "Developers" },
  { id: 3, name: "Designers" },
  { id: 4, name: "Gaming" },
  { id: 5, name: "Movies" },
];

// Dummy users
const users = [
  { id: 999, name: "Me" }, // This represents the current user
  { id: 1, name: "Alice" },
  { id: 2, name: "Bob" },
];

const GroupChat = () => {
  const [activeGroup, setActiveGroup] = useState(null);
  const [messages, setMessages] = useState([]);
  const [messageInput, setMessageInput] = useState("");

  const currentUser = users.find((user) => user.id === 999);

  // Handle selecting a group
  const handleGroupChange = (event) => {
    const selectedGroup = groups.find((group) => group.id === Number(event.target.value));
    setActiveGroup(selectedGroup);
    setMessages([
      { id: 1, text: `Welcome to ${selectedGroup.name}!`, senderId: 1 },
      { id: 2, text: "Hello there!", senderId: 2 },
    ]);
  };

  // Handle sending a new message
  const handleSendMessage = () => {
    if (messageInput.trim() !== "") {
      setMessages([
        ...messages,
        { id: messages.length + 1, text: messageInput, senderId: currentUser.id },
      ]);
      setMessageInput("");
    }
  };

  return (
    <Flex height="100vh" direction="column" bg="#000">
      {/* Navigation Bar */}
      <Flex p="4" borderBottom="1px solid #444" align="center">
  {/* Navigation Menu (Top Left) */}
  <Box>
    <Navigation />
  </Box>

  {/* Group Selection Dropdown (Shifted Right) */}
  <Select
    placeholder="Select a group"
    onChange={handleGroupChange}
    bg="#333"
    color="#fff"
    border="1px solid #444"
    _hover={{ bg: "#222" }}
    _focus={{ borderColor: "#ff0080" }}
    flex="1"
    ml="70px" // Prevents overlap with menu button
  >
    {groups.map((group) => (
      <option key={group.id} value={group.id} style={{ color: "black", background: "#fff" }}>
        {group.name}
      </option>
    ))}
  </Select>
</Flex>


      {/* Chat Messages */}
      <Box flex="1" p="4" overflowY="auto">
        {activeGroup ? (
          messages.map((msg) => {
            const sender = users.find((user) => user.id === msg.senderId) || { name: "Unknown" };
            return (
              <Flex
                key={msg.id}
                justify={msg.senderId === currentUser.id ? "flex-end" : "flex-start"}
              >
                <Box
                  p="2"
                  mb="2"
                  maxW="70%"
                  bg={msg.senderId === currentUser.id ? "#ff0080" : "#333"}
                  border="1px solid #444"
                  borderRadius="md"
                  color="#fff"
                >
                  <Text fontSize="sm" fontWeight="bold">
                    {sender.name}
                  </Text>
                  <Text>{msg.text}</Text>
                </Box>
              </Flex>
            );
          })
        ) : (
          <Flex flex="1" align="center" justify="center">
            <Text color="#fff">Select a group to start chatting.</Text>
          </Flex>
        )}
      </Box>

      {/* Message Input */}
      {activeGroup && (
        <Flex p="4" borderTop="1px solid #444">
          <Input
            flex="1"
            placeholder="Type a message..."
            bg="#333"
            color="#fff"
            border="1px solid #444"
            value={messageInput}
            onChange={(e) => setMessageInput(e.target.value)}
            _placeholder={{ color: "#fff" }}
          />
          <Button ml="4" bg="#ff0080" color="#fff" _hover={{ bg: "#ff0080" }} onClick={handleSendMessage}>
            Send
          </Button>
        </Flex>
      )}
    </Flex>
  );
};

export default GroupChat;
