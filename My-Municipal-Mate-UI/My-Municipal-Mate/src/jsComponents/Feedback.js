import React, { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { getToken } from "../service/AuthService"; 
import "../components/Feedback.css";

const Feedback = () => {
  const [rating, setRating] = useState(0);
  const [formdata, setFormData] = useState({
    name: "",
    email: "",
    department: "",
    feedback: "",
    rating: 0,
  });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [id]: value,
    }));
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    
    const token = getToken();
    const email = getEmailFromToken(token);

    try {
      await axios.post(
        `http://localhost:8081/api/feedback/${email}`, 
        {
          rating: formdata.rating,
          comment: formdata.feedback,
        },
        {
          headers: {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json",
          },
        }
      );
      navigate("/success"); 
    } catch (err) {
      const errorMessage = err.response?.data?.message || "Please Sign-In first";
      setError(errorMessage);
    }
  };

  const handleStarClick = (index) => {
    const newRating = index + 1;
    setRating(newRating);
    setFormData((prev) => ({
      ...prev,
      rating: newRating,
    }));
  };

  const renderStars = () => {
    return Array.from({ length: 5 }, (_, index) => (
      <svg
        key={index}
        onClick={() => handleStarClick(index)}
        className={`star-icon ${rating > index ? "filled" : ""}`}
        xmlns="http://www.w3.org/2000/svg"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path
          strokeLinecap="round"
          strokeLinejoin="round"
          strokeWidth={2}
          d="M11.049 2.927c.3-.921 1.6-.921 1.899 0l1.1 3.413a1.8 1.8 0 001.704 1.171l3.565.261c.964.071 1.353 1.247.653 1.881l-2.68 2.303a1.8 1.8 0 00-.526 1.872l.712 3.63c.206 1.021-1.055 1.799-1.979 1.271L12 15.658l-3.378 2.11c-.924.528-2.185-.25-1.979-1.271l.712-3.63a1.8 1.8 0 00-.526-1.872l-2.68-2.303c-.7-.634-.311-1.81.653-1.881l3.565-.261a1.8 1.8 0 001.704-1.171l1.1-3.413z"
        />
      </svg>
    ));
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
    <div id="feedback-container">
      <form onSubmit={handleSubmit} className="form">
        <h1 className="feedback-title">Feedback Form</h1>

        

        <div className="form-group">
          <label htmlFor="feedback" className="form-label" style={{ color: 'white' }}>
            Feedback
          </label>
          <textarea
            id="feedback"
            value={formdata.feedback}
            onChange={handleChange}
            placeholder="Enter your feedback here"
            className="form-textarea"
          />
        </div>

        <div className="form-group">
          <label className="form-label" style={{ color: 'white' }}>Rating</label>
          <div className="stars">{renderStars()}</div>
        </div>

        {error && <div className="error-message">{error}</div>}

        <button type="submit" className="submit-button">
          Submit
        </button>
      </form>
    </div>
  );
};

export default Feedback;
