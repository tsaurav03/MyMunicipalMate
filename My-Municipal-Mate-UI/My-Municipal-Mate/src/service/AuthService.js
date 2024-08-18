import {jwtDecode} from "jwt-decode"; 
const TOKEN_KEY = "authToken";

export const getToken = () => {
  //return localStorage.getItem(TOKEN_KEY);
   
  let token = localStorage.getItem(TOKEN_KEY);

  if (!token) {
    // Check for token in cookies if not found in localStorage
    const cookieToken = document.cookie
      .split("; ")
      .find((row) => row.startsWith("jwtToken="))
      ?.split("=")[1];

    token = cookieToken ? cookieToken : null;
  }

  return token;
};

export const setToken = (token) => {
  localStorage.setItem(TOKEN_KEY, token);

  // Set token in cookies with expiration date
  document.cookie = `jwtToken=${token}; path=/; max-age=3600;`;
};

export const removeToken = () => {
  localStorage.removeItem(TOKEN_KEY);

  //remove token from cookies
 document.cookie = "jwtToken=; path=/; max-age=0;";
};

export const getUsername = (token) => {
  try {
    const decodedToken = jwtDecode(token);
    return decodedToken.sub; 
  } catch (error) {
    return null;
  }
};

