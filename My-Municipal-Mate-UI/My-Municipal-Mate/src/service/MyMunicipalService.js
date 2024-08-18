import axios from "axios";

const API_URL = "http://localhost:8081/api";

// Get the JWT token from local storage
const getAuthToken = () => localStorage.getItem("authToken");

const getAuthHeaders = () => {
  const token = getAuthToken();
  return token
    ? {
        Authorization: `Bearer ${token}`,
        "Content-Type": "application/json",
      }
    : {};
};

const MyMunicipalService = {
  signIn: (usernameOrEmail, password) =>
    axios.post(`${API_URL}/auth/signin`, { usernameOrEmail, password }),

  registerUser: (user) => axios.post(`${API_URL}/auth/signup`, user),

  forgotPassword: (forgotPasswordDTO) =>
    axios.post(`${API_URL}/auth/forgotpassword`, null, {
      params: forgotPasswordDTO,
    }),

  verifyOtp: (verifyOtpDTO) =>
    axios.post(`${API_URL}/auth/verify-otp`, verifyOtpDTO),

  adminSignIn: (usernameOrEmail, password) =>
    axios.post(`${API_URL}/auth/admin/signin`, { usernameOrEmail, password }),

  fetchComplaints: async () => {
    try {
      const response = await axios.get(`${API_URL}/admin/complaints`, {
        headers: getAuthHeaders(),
      });
      return response.data;
    } catch (error) {
      console.error("Error fetching complaints:", error);
      throw error;
    }
  },
};

export default MyMunicipalService;
