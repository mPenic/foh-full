// src/components/PublicHomePage.jsx
import React from "react";
import { Link as RouterLink } from "react-router-dom";
import { Box, Button, Heading, Text } from "@chakra-ui/react";

/**
 * A publicly accessible page (unauthenticated).
 * Just a heading, some text, and a button to go to "/login".
 */
const PublicHomePage = () => {
  return (
    <Box
      bg="black"
      color="white"
      minH="100vh"
      display="flex"
      flexDirection="column"
      justifyContent="center"
      alignItems="center"
      textAlign="center"
      px={{ base: 6, md: 0 }} // Extra horizontal padding on small screens
    >
      <Heading mb={4}>Welcome to MyReze!</Heading>
      <Text fontSize="xl" mb={6}>
        This is a public landing page. Please sign in to continue.
      </Text>
      <Button
        as={RouterLink}
        to="/login"
        bg="#ff0080"
        color="white"
        size="lg"
        _hover={{ bg: "#cc006f" }}
      >
        Sign In
      </Button>
    </Box>
  );
};

export default PublicHomePage;
