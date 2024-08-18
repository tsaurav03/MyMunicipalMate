import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { getToken } from "../service/AuthService";
import "../components/Complain.css";

const Complain = () => {
  const [complaintDescription, setComplaintDescription] = useState("");
  const [location, setLocation] = useState("");
  const [complaintType, setComplaintType] = useState("garbage-management");
  const [file, setFile] = useState(null);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
  };

  const handleSubmit = async (event) => {
    event.preventDefault();

    // Extract token and email from local storage
    const token = getToken();
    const email = getEmailFromToken(token);

    // Create FormData for file and complaint JSON
    const formData = new FormData();
    if (file) {
      formData.append("file", file);
    }

    
    const complaintData = JSON.stringify({
      complaintDescription,
      location,
    });
    formData.append(
      "complaint",
      new Blob([complaintData], { type: "application/json" })
    );

    try {
      
      await axios.post(
        `http://localhost:8081/api/complaints/${complaintType}/${email}`,
        formData,
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "multipart/form-data",
          },
        }
      );

      navigate("/success"); 
    } catch (err) {
     
      const errorMessage = err.response?.data?.message || "An error occurred";
      setError(errorMessage);
    }
  };

  const getEmailFromToken = (token) => {
    if (!token) return null;
    try {
      const base64Payload = token.split(".")[1];
      const payload = JSON.parse(atob(base64Payload));
      return payload.sub; 
    } catch (error) {
      return null;
    }
  };

  return (
    <div className="add-complaint-container">
      <h2>Add a Complaint</h2>
      <form onSubmit={handleSubmit}>
        <div className="form-group">
          <label htmlFor="complaintType">Complaint Type:</label>
          <select
            id="complaintType"
            value={complaintType}
            onChange={(e) => setComplaintType(e.target.value)}
            required
          >
            <option value="garbage-management">Garbage Management</option>
            <option value="water-supply">Water Supply</option>
            <option value="road-repair">Road Repair</option>
            <option value="sanitation-issues">Sanitation Issues</option>
            <option value="traffic-management">Traffic Management</option>
            <option value="environmental-hazards">Environmental Hazards</option>
            <option value="fire-safety">Fire Safety</option>
            <option value="electricity-management">
              Electricity Management
            </option>
          </select>
        </div>
        <div className="form-group">
          <label htmlFor="complaintDescription">Complaint Description:</label>
          <textarea
            id="complaintDescription"
            value={complaintDescription}
            onChange={(e) => setComplaintDescription(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="location">Location:</label>
          <input
            id="location"
            type="text"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
            required
          />
        </div>
        <div className="form-group">
          <label htmlFor="file">Choose Image (optional):</label>
          <input
            id="file"
            type="file"
            accept="image/*"
            onChange={handleFileChange}
          />
        </div>
        {error && <div className="error-message">{error}</div>}
        <button type="submit">Submit Complaint</button>
      </form>
    </div>
  );
};

export default Complain;
