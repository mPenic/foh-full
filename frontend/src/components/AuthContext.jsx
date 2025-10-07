import { createContext, useContext, useEffect, useState } from "react";

const AuthContext = createContext();

export const AuthProvider = ({ children }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [isLoading, setIsLoading] = useState(true);
  const [featureFlags, setFeatureFlags] = useState({ featCh: false, featKb: false, featRe: false });
  const [userDetails, setUserDetails] = useState({ companyId: null, roleId: null, userId: null, username: "" });


  const checkAuth = async () => {
    setIsLoading(true);
    try {
      const authResponse = await fetch("http://localhost:8080/api/check-auth", {
        credentials: "include",
      });
      if (!authResponse.ok) {
        setIsAuthenticated(false);
        setFeatureFlags({ featCh: false, featKb: false, featRe: false });
        setUserDetails({ companyId: null, roleId: null, userId: null, username: "" });
      } else {
        setIsAuthenticated(true);
        
        const [flagResponse, userResponse] = await Promise.all([
          fetch("http://localhost:8080/api/feature-flags", { credentials: "include" }),
          fetch("http://localhost:8080/api/user-details", { credentials: "include" }),
        ]);
        if (flagResponse.ok) {
          setFeatureFlags(await flagResponse.json());
        }
        if (userResponse.ok) {
          setUserDetails(await userResponse.json());
        }
      }
    } catch (e) {
      console.error("Error during auth check:", e);
      setIsAuthenticated(false);
      setFeatureFlags({ featCh: false, featKb: false, featRe: false });
      setUserDetails({ companyId: null, roleId: null, userId: null, username: "" });
    } finally {
      setIsLoading(false);
    }
  };

  const logout = async () => {
    try {
      // Make sure the method here matches your Spring Security logout configuration.
      // For Spring Security, POST is common.
      const response = await fetch("http://localhost:8080/logout", {
        method: "POST",
        credentials: "include",
      });
      if (!response.ok) {
        console.error("Logout failed on the server");
      }
    } catch (e) {
      console.error("Error during logout:", e);
    } finally {
      // Clear local auth state
      setIsAuthenticated(false);
      setFeatureFlags({ featCh: false, featKb: false, featRe: false });
      setUserDetails({ companyId: null, roleId: null, userId: null, username: "" });
    }
  };

  useEffect(() => {
    checkAuth();
  }, []);

  return (
    <AuthContext.Provider value={{ isAuthenticated, setIsAuthenticated, featureFlags, userDetails, checkAuth, isLoading, logout }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => useContext(AuthContext);
