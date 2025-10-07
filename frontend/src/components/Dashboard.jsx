// src/components/Dashboard.jsx
import React from "react";
import { Link as RouterLink } from "react-router-dom";
import { Box, Button, Heading, Text, VStack } from "@chakra-ui/react";
import { useAuth } from "./AuthContext";

/**
 * This is the "landing page" for *all* authenticated users.
 * We show only the modules they have access to: featRe, featKb, featCh.
 */
const Dashboard = () => {
  const { featureFlags } = useAuth();

  return (
    <Box 
      minH="100vh" 
      bg="black" 
      color="white" 
      display="flex" 
      flexDirection="column" 
      alignItems="center" 
      justifyContent="center"
    >
      <Heading mb={6}>Welcome to MyReze!</Heading>
      <Text mb={6}>
        This is your main dashboard. Select a module below:
      </Text>

      <VStack spacing={4}>
        {/* UserManagement */}
        <Button
          as={RouterLink}
          to="/user-management"
          bg="#ff0080"
          _hover={{ opacity: 0.8 }}
          w="200px"
        >
          User Management
        </Button>
        {/* Kanban */}
        {featureFlags.featKb ? (
          <Button
            as={RouterLink}
            to="/kanban"
            bg="#ff0080"
            _hover={{ opacity: 0.8 }}
            w="200px"
          >
            Kanban
          </Button>
        ) : (
          <Text fontSize="sm" color="gray.500">
            Kanban Disabled
          </Text>
        )}

        {/* Chat */}
        {featureFlags.featCh ? (
          <Button
            as={RouterLink}
            to="/groupChat"
            bg="#ff0080"
            _hover={{ opacity: 0.8 }}
            w="200px"
          >
            Group Chat
          </Button>
        ) : (
          <Text fontSize="sm" color="gray.500">
            Chat Disabled
          </Text>
        )}

        {/* Reservations */}
        {featureFlags.featRe ? (
          <Button
            as={RouterLink}
            to="/reservations"
            bg="#ff0080"
            _hover={{ opacity: 0.8 }}
            w="200px"
          >
            Reservations
          </Button>
        ) : (
          <Text fontSize="sm" color="gray.500">
            Reservations Disabled
          </Text>
        )}
      </VStack>
    </Box>
  );
};

export default Dashboard;
