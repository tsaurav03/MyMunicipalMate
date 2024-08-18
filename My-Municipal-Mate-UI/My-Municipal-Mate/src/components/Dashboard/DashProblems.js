import React, { useEffect, useState } from "react";
import axios from "axios";
import "./DashProblems.css"; // Import the CSS file

const DashProblems = () => {
  const [complaints, setComplaints] = useState([]);
  const [error, setError] = useState(null);

  const getAuthToken = () => localStorage.getItem("authToken");

  useEffect(() => {
    const fetchComplaints = async () => {
      try {
        const token = getAuthToken();
        const response = await axios.get(
          "http://localhost:8081/api/admin/complaints",
          {
            headers: {
              Authorization: `Bearer ${token}`,
            },
          }
        );
        setComplaints(response.data);
      } catch (error) {
        console.error("Error fetching complaints:", error);
        setError("Failed to load complaints");
      }
    };

    fetchComplaints();
  }, []);

  return (
    <div className="dash-problems-container">
      <div id="complaintManagement">
        <h2 className="text-center">Complaint Management</h2>
        {error && <p className="text-danger">{error}</p>}
        <div className="card">
          <div className="card-header text-center">
            <i className="fas fa-tasks"></i> Complaints
          </div>
          <div className="card-body">
            <table className="table-bordered">
              <thead>
                <tr>
                  <th>Id</th>
                  <th>Image</th>
                  <th>Username</th>
                  <th>Description</th>
                  <th>Location</th>
                  <th>Status</th>
                  <th>Complaint Type</th>
                </tr>
              </thead>
              <tbody>
                {complaints.length > 0 ? (
                  complaints.map((complaint, index) => (
                    <tr key={complaint.id}>
                      <td>{complaint.id}</td>
                      <td>
                        <div className="complaint-image-container">
                          <img
                            src={`data:image/jpeg;base64,${complaint.imageData}`}
                            alt="Complaint"
                            className="complaint-image"
                          />
                        </div>
                      </td>
                      <td>{complaint.username}</td>
                      <td>{complaint.complaintDescription}</td>
                      <td>{complaint.location}</td>
                      <td>{complaint.status}</td>
                      <td>{complaint.complaintType}</td>
                    </tr>
                  ))
                ) : (
                  <tr>
                    <td colSpan="7">No complaints available</td>
                  </tr>
                )}
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  );
};

export default DashProblems;
