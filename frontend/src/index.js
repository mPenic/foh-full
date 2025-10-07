import React, { useEffect } from "react";
import ReactDOM from "react-dom/client";
import { BrowserRouter } from "react-router-dom";
import { ChakraProvider } from "@chakra-ui/react";
import App from "./App";
import {  AuthProvider, useAuth } from "./components/AuthContext";
import webSocketService from "./websocket";


const WebSocketInitializer = () => {
  const { isAuthenticated, userDetails } = useAuth();

  useEffect(() => {
    if (isAuthenticated && userDetails.companyId && userDetails.roleId) {
      webSocketService.connect(() => {
        webSocketService.subscribeToTasks(
          userDetails.companyId,
          "kanban",
          userDetails.roleId,
          (taskUpdate) => {
            console.log("Task update:", taskUpdate);
          }
        );
      });
    }

    return () => webSocketService.disconnect();
  }, [isAuthenticated, userDetails]);

  return null;
};

const rootElement = document.getElementById("root");
const root = ReactDOM.createRoot(rootElement);

root.render(
  <BrowserRouter>
    <AuthProvider>
      <ChakraProvider>
          <WebSocketInitializer />
          <App />
      </ChakraProvider>
    </AuthProvider>
  </BrowserRouter>
);
