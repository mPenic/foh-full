import React from "react";
import { Routes, Route, Navigate } from "react-router-dom";
import { AuthProvider, useAuth } from "./components/AuthContext";
import PublicHomePage from "./components/PublicHomePage";
import LoginPage from "./components/LoginPage";
import Dashboard from "./components/Dashboard";
import Reservations from "./components/Reservations";
import Kanban from "./components/Kanban";
import GroupChat from "./components/GroupChat";
import UserMgmt from "./components/UserMgmt";

const PrivateRoute = ({ children }) => {
  const { isAuthenticated, isLoading } = useAuth();
  if (isLoading) return <div>Loading...</div>;
  return isAuthenticated ? children : <Navigate to="/" replace />;
};

const FeatureFlagRoute = ({ children, feature }) => {
  const { isAuthenticated, isLoading, featureFlags } = useAuth();
  if (isLoading) return <div>Loading...</div>;
  if (!isAuthenticated) return <Navigate to="/" replace />;
  return featureFlags[feature] ? (
    children
  ) : (
    <Navigate to="/dashboard" replace />
  );
};

const App = () => {
  return (
    <Routes>
      {/* Public routes */}
      <Route path="/" element={<PublicHomePage />} />
      <Route path="/login" element={<LoginPage />} />

      {/* Everyone must be authenticated to see these */}
      <Route path="/dashboard" element={<Dashboard />} />

      <Route path="/user-management" element={<UserMgmt />} />

      <Route path="/reservations" element={<Reservations />} />

      <Route path="/kanban" element={<Kanban />} />

      <Route
        path="/groupChat"
        element={
          <FeatureFlagRoute feature="featCh">
            <GroupChat />
          </FeatureFlagRoute>
        }
      />

      {/* Catch-all*/}
      <Route path="*" element={<Navigate to="/" replace />} />
    </Routes>
  );
};

export default App;
