import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import MyMunicipalMateService from "../service/MyMunicipalService";
import "../components/RegisterComponent.css";

function RegisterComponent() {
  const [user, setUser] = useState({
    name: "",
    lastName: "",
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate(); // Initialize useNavigate

  const handleChange = (e) => {
    const { name, value } = e.target;
    setUser({ ...user, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (
      !user.firstName ||
      !user.lastName ||
      !user.username ||
      !user.email ||
      !user.password ||
      !user.confirmPassword
    ) {
      setErrorMessage("All fields are required");
      return;
    }

    if (user.password !== user.confirmPassword) {
      setErrorMessage("Passwords do not match");
      return;
    }

    try {
      const response = await MyMunicipalMateService.registerUser(user);
      if (response.data.success) {
        alert("Registration successful");
        navigate("/signin"); // Redirect to sign-in page
      } else {
        setErrorMessage(response.data.message || "Registration failed");
      }
    } catch (error) {
      setErrorMessage(error.response?.data?.message || "Registration failed");
    }
  };

  return (
    <div id="bg">
      <div className="register-form">
        <h2>Register</h2>
        {errorMessage && <p className="error-message">{errorMessage}</p>}
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            name="firstName"
            value={user.firstName}
            onChange={handleChange}
            placeholder="Name"
          />
          <input
            type="text"
            name="lastName"
            value={user.lastName}
            onChange={handleChange}
            placeholder="Last Name"
          />
          <input
            type="text"
            name="username"
            value={user.username}
            onChange={handleChange}
            placeholder="Username"
          />
          <input
            type="email"
            name="email"
            value={user.email}
            onChange={handleChange}
            placeholder="Email"
          />
          <input
            type="password"
            name="password"
            value={user.password}
            onChange={handleChange}
            placeholder="Password"
          />
          <input
            type="password"
            name="confirmPassword"
            value={user.confirmPassword}
            onChange={handleChange}
            placeholder="Confirm Password"
          />
          <button type="submit">Register</button>
        </form>
      </div>
    </div>
  );
}

export default RegisterComponent;
