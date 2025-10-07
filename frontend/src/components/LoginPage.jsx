import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Box, Button, Flex, Heading, Input, Text, VStack, Image } from "@chakra-ui/react";
import { useAuth } from "./AuthContext";
import logo from "../images/myreze-high-resolution-logo-transparent.png";

const LoginPage = () => {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();
  const { checkAuth, isAuthenticated } = useAuth(); // ✅ Extract checkAuth from useAuth()

  useEffect(() => {
    if (isAuthenticated) {
      navigate("/dashboard");
    }
  }, [isAuthenticated, navigate]);

  const handleLogin = async (e) => {
    e.preventDefault();
    setErrorMessage("");

    try {
      const response = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ username, password }),
        credentials: "include",
      });

      if (!response.ok) throw new Error("Invalid username or password");

      await checkAuth(); // ✅ Ensure authentication state updates
      navigate("/dashboard"); // ✅ Redirect after successful login
    } catch (error) {
      setErrorMessage(error.message);
    }
  };

  return (
    <Flex height="100vh" align="center" justify="center" bg="black">
      <VStack spacing={6} p={6} bg="#111" borderRadius="lg" boxShadow="lg" w="90%" maxW="400px">
        <Image src={logo} alt="Myreze Logo" boxSize="100px" />
        <Heading size="md" color="white">Login</Heading>
        {errorMessage && <Text color="red.400">{errorMessage}</Text>}
        <Input
          placeholder="Username"
          bg="gray.800"
          color="white"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          _placeholder={{ color: "gray.500" }}
        />
        <Input
          placeholder="Password"
          type="password"
          bg="gray.800"
          color="white"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          _placeholder={{ color: "gray.500" }}
        />
        <Button
          bgGradient="linear(to-r, #ff0080, #00bfff)"
          color="white"
          w="full"
          onClick={handleLogin}
          _hover={{ opacity: 0.9 }}
        >
          Login
        </Button>
      </VStack>
    </Flex>
  );
};

export default LoginPage;
