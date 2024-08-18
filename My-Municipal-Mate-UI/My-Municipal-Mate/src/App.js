import React, { useState } from "react";
import { Routes, Route,Navigate } from "react-router-dom";
import Navigationbar from "./jsComponents/Navigationbar";
import FeedComponent from "./jsComponents/FeedCard";
// import Footer from "./jsComponents/Footer";
import Complain from "./jsComponents/Complain";
import SuccessPage from "./jsComponents/SuccessPage";
import About from "./jsComponents/About";
import Contactus from "./jsComponents/Contactus";
import SignInPage from "./jsComponents/SignInPage"; 
import RegisterComponent from "./jsComponents/RegisterComponent";
import ForgetPassword from "./jsComponents/ForgetPassword";
import Feedback from "./jsComponents/Feedback";
import Dashboard from "./jsComponents/Dashboard";
import Admin from "./jsComponents/Admin";
import { getToken } from "./service/AuthService";
import SignIn from "./jsComponents/SignInPage";
import "./i18n";

function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(!!getToken());

  const handleLogin = () => {
    setIsAuthenticated(true);
  };

  // const handleAdminLogin = () => {
  //   setIsAdminAuthenticated(true);
  // };

  const handleLogout = () => {
    setIsAuthenticated(false);
    // setIsAdminAuthenticated(false);
  };

  return (
    <div className="App">
      <Navigationbar onLogout={handleLogout} />
      <Routes>
        <Route path="/" element={<About />} />
        <Route path="/ContactUs" element={<Contactus />} />
        <Route path="/Feed" element={<FeedComponent />} />
        <Route
          path="/complain"
          element={
            isAuthenticated ? (
              <Complain />
            ) : (
              <SignInPage onLogin={handleLogin} />
            )
          }
        
        />
        <Route path="/signin" element={<SignIn></SignIn>} />
        <Route path="/register" element={<RegisterComponent />} />
        <Route path="/forgot-password" element={<ForgetPassword />} />
        <Route path="/feedback" element={<Feedback />} />
        <Route
          path="/dashadmin"
          element={
            isAuthenticated ? <Dashboard /> : <Navigate to="/adminLogin" />
          }
        />
        <Route path="/success" element={<SuccessPage />} />
        <Route path="/adminLogin" element={<Admin onLogin={handleLogin} />} />
        <Route path="/password-settings" element={<ForgetPassword />} />
      </Routes>
      {/* <Footer /> */}
    </div>
  );
}

export default App;
