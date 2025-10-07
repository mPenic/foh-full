import React, { useState } from "react";
import { Box, IconButton, VStack, Link, Button  } from "@chakra-ui/react";
import { FaBars, FaTimes } from "react-icons/fa";
import { useAuth } from "./AuthContext";
import { useNavigate } from "react-router-dom";

const Navigation = () => {
  const [isNavOpen, setIsNavOpen] = useState(false);
  const { isAuthenticated, featureFlags, logout } = useAuth();
  const navigate = useNavigate();

  if (!isAuthenticated) return null; 

  const toggleNav = () => {
    setIsNavOpen(!isNavOpen);
  };

  const handleLogout = async () => {
    await logout(); // Calls the logout function in AuthContext
    navigate("/"); // Redirects to the PublicHomePage
  };

  return (
    <>
      {/* Sidebar */}
      <Box
        position="fixed"
        left={isNavOpen ? "0" : "-250px"} // Slide-in effect
        top="0"
        width="250px"
        height="100vh"
        bg="#111"
        boxShadow="xl"
        transition="left 0.3s ease-in-out"
        zIndex="20"
        p="4"
      >
        {/* Close Button */}
        <IconButton
          icon={<FaTimes />}
          onClick={toggleNav}
          aria-label="Close Menu"
          bg="transparent"
          color="white"
          fontSize="24px"
          _hover={{ color: "#ff0080" }}
          mb="4"
        />

        {/* Navigation Links */}
        <VStack align="flex-start" spacing="4">
          {/* Always show Dashboard link */}
          <Link
            href="/dashboard"
            fontSize="18px"
            color="white"
            _hover={{ color: "#ff0080" }}
          >
            Dashboard
          </Link>
          
          <Link href="/user-management" fontSize="18px" color="white" _hover={{ color: "#ff0080" }}>
            User Management
          </Link>

          {/* Conditionals for features */}
          {featureFlags.featKb && (
            <Link
              href="/kanban"
              fontSize="18px"
              color="white"
              _hover={{ color: "#ff0080" }}
            >
              Kanban
            </Link>
          )}

          {featureFlags.featCh && (
            <Link
              href="/groupChat"
              fontSize="18px"
              color="white"
              _hover={{ color: "#ff0080" }}
            >
              Chat
            </Link>
          )}

          {featureFlags.featRe && (
            <Link
              href="/reservations"
              fontSize="18px"
              color="white"
              _hover={{ color: "#ff0080" }}
            >
              Reservations
            </Link>
          )}

          {/* Logout Button */}
          <Button onClick={handleLogout} bg="red.600" color="white" mt="4">
            Logout
          </Button>
        </VStack>
      </Box>

      {/* Hamburger Menu Button (Fixed in Top Left) */}
      {!isNavOpen && (
        <IconButton
          icon={<FaBars />}
          onClick={toggleNav}
          aria-label="Open Menu"
          position="fixed"
          top="15px"
          left="15px"
          zIndex="25"
          bgGradient="linear(to-r, #ff0080, #00bfff)"
          color="white"
          borderRadius="full"
          boxSize="50px"
          fontSize="24px"
          // Fully opaque
        />
      )}
    </>
  );
};

export default Navigation;
