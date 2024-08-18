import React, { useState } from "react";
import axios from "axios";
import "./DashProfile.css";
import { useNavigate } from "react-router-dom";

const AssignTeamComponent = () => {
  const [complaintId, setComplaintId] = useState("");
  const [message, setMessage] = useState("");
  const navigate = useNavigate();

  // Function to get auth token from local storage
  const getAuthToken = () => localStorage.getItem("authToken");

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await axios.put(
        `http://localhost:8081/api/admin/assign-team/${complaintId}`,
        {},
        {
          headers: {
            Authorization: `Bearer ${getAuthToken()}`,
          },
        }
      );
      setMessage(response.data);

      //navigate("/complaints");
    } catch (error) {
      if (error.response && error.response.status === 401) {
        setMessage("Unauthorized: Please log in again.");
      } else if (error.response && error.response.status === 404) {
        setMessage("Complaint not found.");
      } else {
        setMessage(error.response?.data || "An error occurred");
      }
    }
  };

  return (
    <>
      <div className="container">
        <div className="assign-team-form">
          <h2>Assign Team to Complaint</h2>
          <form onSubmit={handleSubmit}>
            <div className="form-group">
              <label htmlFor="complaintId">Complaint ID</label>
              <input
                type="text"
                id="complaintId"
                value={complaintId}
                onChange={(e) => setComplaintId(e.target.value)}
                required
              />
            </div>
            <button type="submit">Assign Team</button>
          </form>
          {message && <p>{message}</p>}
        </div>
      </div>
    </>
  );
};

export default AssignTeamComponent;
