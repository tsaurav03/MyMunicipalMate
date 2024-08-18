import React from "react";
import { Link } from "react-router-dom";
import "../components/SuccessPage.css";

const SuccessPage = () => {
  return (
    <div className="success-page">
      <div className="success-content">
        <h1>Thank You!</h1>
        <p>Your complaint has been successfully submitted.</p>
        <p>
          Thank you for using MyMunicipalMate. The concerned authorities will
          take action quickly.
        </p>
        <Link to="/" className="back-home">
          Go Back to Home
        </Link>
      </div>
    </div>
  );
};

export default SuccessPage;
