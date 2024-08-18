import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import { AiOutlineMail } from "react-icons/ai";
import { FaEye, FaEyeSlash } from "react-icons/fa";
import { SlLock } from "react-icons/sl";
import MyMunicipalService from "../service/MyMunicipalService";
import "../components/ForgetPassword.css";

const ForgetPassword = () => {
  const navigate = useNavigate();
  const [passVisible, setPassVisible] = useState(false);
  const [cpassVisible, setCPassVisible] = useState(false);
  const [formData, setFormData] = useState({});
  const [verified, setVerified] = useState("Verify");
  const [otp, setOtp] = useState(["", "", "", ""]);

  const handlePassVisible = () => {
    setPassVisible((prev) => !prev);
  };

  const handleCPassVisible = () => {
    setCPassVisible((prev) => !prev);
  };

  const handleChange = (e) => {
    const { id, value } = e.target;
    setFormData((prev) => ({
      ...prev,
      [id]: value,
    }));
  };

  const handleOtpChange = (e, index) => {
    const { value } = e.target;
    const newOtp = [...otp];
    newOtp[index] = value.slice(0, 1); // Allow only one digit per input
    setOtp(newOtp);

    if (index < 3 && value) {
      document.getElementById(`otp-${index + 1}`).focus();
    }
  };

  const handleVerify = async (email) => {
    try {
      const response = await MyMunicipalService.forgotPassword({ email });
      if (response.status === 200) {
        setVerified("Verified");
      } else {
        setVerified("Not Verified");
      }
    } catch (error) {
      console.error("Verification failed", error);
      setVerified("Not Verified");
    }
  };

  //
  const handleSubmit = async (e) => {
    e.preventDefault();
    if (formData.password !== formData.confirmpassword) {
      alert("Passwords do not match");
      return;
    }

    if (verified === "Verified") {
      const otpCode = otp.join("");
      try {
        await MyMunicipalService.verifyOtp({
          email: formData.email,
          otp: otpCode,
          newPassword: formData.password,
          confirmPassword: formData.confirmpassword,
        });
        console.log("Password reset successful");
        navigate("/signin"); // Redirect to sign-in page
      } catch (error) {
        console.error("Error resetting password", error);
      }
    } else {
      alert("Email not verified");
    }
  };

  const handleSignUpRedirect = () => {
    navigate("/signin");
  };

  return (
    <div className="container">
      <form className="form" onSubmit={handleSubmit}>
        <h1 className="form-title">Reset Password</h1>
        <div className="form-group">
          <div className="input-group">
            <AiOutlineMail className="icon" />
            <input
              type="email"
              placeholder="xyz@email.com"
              id="email"
              className="input"
              onChange={handleChange}
            />
            <h2
              className={`status ${
                verified === "Verify"
                  ? "status-verify"
                  : verified === "Verified"
                  ? "status-verified"
                  : "status-not-verified"
              }`}
              onClick={() => handleVerify(formData.email)}
            >
              {verified}
            </h2>
          </div>

          {verified === "Verified" && (
            <div className="otp-group">
              {otp.map((digit, index) => (
                <input
                  key={index}
                  // type="number"
                  // min="0"
                  // max="9"
                  id={`otp-${index}`}
                  className="otp-input"
                  value={digit}
                  onChange={(e) => handleOtpChange(e, index)}
                  onFocus={(e) => e.target.select()}
                />
              ))}
            </div>
          )}
          <div className="input-group">
            <SlLock className="icon" />
            <input
              type={passVisible ? "text" : "password"}
              placeholder="Password"
              id="password"
              className="input"
              disabled={verified !== "Verified"}
              onChange={handleChange}
            />
            {passVisible ? (
              <FaEye className="eye-icon" onClick={handlePassVisible} />
            ) : (
              <FaEyeSlash className="eye-icon" onClick={handlePassVisible} />
            )}
          </div>
          <div className="input-group">
            <SlLock className="icon" />
            <input
              type={cpassVisible ? "text" : "password"}
              placeholder="Confirm Password"
              id="confirmpassword"
              className="input"
              disabled={verified !== "Verified"}
              onChange={handleChange}
            />
            {cpassVisible ? (
              <FaEye className="eye-icon" onClick={handleCPassVisible} />
            ) : (
              <FaEyeSlash className="eye-icon" onClick={handleCPassVisible} />
            )}
          </div>
          <div className="form-actions">
            <button type="submit" className="submit-button">
              Reset Password
            </button>
            {/* <h4 className="credentials-link">Remember Credentials?</h4> */}
            <h4 className="signup-link">
              Don't have an account?{" "}
              <span className="signup-text" onClick={handleSignUpRedirect}>
                Sign In
              </span>
            </h4>
          </div>
        </div>
      </form>
    </div>
  );
};

export default ForgetPassword;
