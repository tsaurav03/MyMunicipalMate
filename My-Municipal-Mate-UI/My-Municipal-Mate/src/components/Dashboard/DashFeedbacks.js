import React, { useState } from "react";
import axios from "axios";
import "./DashFeedback.css"; // Assuming the CSS is in the same directory

// Function to get auth token from local storage
const getAuthToken = () => localStorage.getItem("authToken");

const AdminComplaintForm = () => {
  const [complaintID, setComplaintID] = useState("");

  const handleResolved = async () => {
    try {
      const response = await axios.patch(
        `http://localhost:8081/api/admin/complaints/${complaintID}/resolved`,
        {},
        {
          headers: {
            Authorization: `Bearer ${getAuthToken()}`,
          },
        }
      );
      alert(response.data);
    } catch (error) {
      alert(`Error: ${error.response.data}`);
    }
  };

  const handleOpen = async () => {
    try {
      const response = await axios.patch(
        `http://localhost:8081/api/admin/complaints/${complaintID}/open`,
        {},
        {
          headers: {
            Authorization: `Bearer ${getAuthToken()}`,
          },
        }
      );
      alert(response.data);
    } catch (error) {
      alert(`Error: ${error.response.data}`);
    }
  };

  return (
    <div className="admin-complaint-form">
      <h2>Admin: Update Complaint Status</h2>
      <input
        type="text"
        placeholder="Enter Complaint ID"
        value={complaintID}
        onChange={(e) => setComplaintID(e.target.value)}
      />
      <br />
      <button onClick={handleResolved}>Mark as Resolved</button>
      <br />
      <button onClick={handleOpen}>Mark as Open</button>
    </div>
  );
};

export default AdminComplaintForm;
