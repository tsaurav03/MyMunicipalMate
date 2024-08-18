import React, { useState } from "react";
import axios from "axios";
import './DashUser.css';

// Function to get auth token from local storage
const getAuthToken = () => localStorage.getItem("authToken");

const AssignRoleForm = () => {
  const [email, setEmail] = useState("");
  const [role, setRole] = useState("");
  const [message, setMessage] = useState("");

  const handleSubmit = async (event) => {
    event.preventDefault();

    try {
      const response = await axios.post(
        "http://localhost:8081/api/admin/assign-role",
        {
          email,
          roleName: role,
        },
        {
          headers: {
            Authorization: `Bearer ${getAuthToken()}`,
          },
        }
      );
      setMessage(response.data.message || "Role assigned successfully");
    } catch (error) {
      setMessage(error.response?.data?.message || "Failed to assign role");
    }
  };

  return (
    <div className="assign-role-form">
      <h2>Assign Role to Citizen</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="email">Email:</label>
          <input
            type="email"
            id="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="role">Role:</label>
          <select
            id="role"
            value={role}
            onChange={(e) => setRole(e.target.value)}
            required
          >
            <option value="" disabled>
              Select a role
            </option>
            <option value="ROLE_ADMIN">Admin</option>
            <option value="ROLE_CITIZEN">Citizen</option>
          </select>
        </div>
        <button type="submit">Assign Role</button>
      </form>
      {message && <p>{message}</p>}
    </div>
  );
};

export default AssignRoleForm;
