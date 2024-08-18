import React, { useState, useEffect } from "react";
import { Link, useNavigate } from "react-router-dom";
import { useTranslation } from "react-i18next";
import "../components/Navigation.css";
import { getToken, getUsername, removeToken } from "../service/AuthService";
import logo from '../assets/images/logo2.png';

const Navigationbar = ({ onLogout }) => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [isOpen, setIsOpen] = useState(false);
  const [username, setUsername] = useState(null);
  const navigate = useNavigate();
  const { t, i18n } = useTranslation();

  useEffect(() => {
    const token = getToken();
    if (token) {
      const username = getUsername(token);
      setUsername(username);
    }
  }, []);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  const toggleDropdown = () => {
    setIsOpen(!isOpen);
  };

  const handleSignOut = () => {
    removeToken(); // Clear token
    setUsername(null);
    onLogout(); 
    navigate("/"); 
  };

  const changeLanguage = (lng) => {
    i18n.changeLanguage(lng);
    setIsOpen(false);
  };

  return (
    <>
      <div id="outer-nav">
        <div id="nav">
          <div id="nav-left">
            <img src={logo} alt="Error" style={{height:'110px', width:'210px', marginTop:'10px'}} />
          </div>
          <div id="nav-center">
            <ul id="nav-center-ul">
              <li><Link to="/">{t('home')}</Link></li>
              <li><Link to="/ContactUs">{t('contactUs')}</Link></li>
              <li><Link to="/feed">{t('feeds')}</Link></li>
              <button id="button">
                <Link to="/complain" id="button-link">{t('complain')}</Link>
              </button>

              <div className={`dropdown ${isOpen ? "show" : ""}`}>
                <button className="dropbtn" onClick={toggleDropdown}>
                  {/* 🌍 World icon */}
                  <i className="fas fa-globe"></i>
                </button>
                <div className="dropdown-content">
                  <button onClick={() => changeLanguage('en')}>En</button>
                  <button onClick={() => changeLanguage('hi')}>हि</button>
                  <button onClick={() => changeLanguage('mr')}>म</button>
                </div>
              </div>
              
            </ul>
          </div>
          <div id="nav-right">
            <i className="fas fa-bars" onClick={toggleMenu}></i>
          </div>
        </div>
      </div>

      <div id="side-menu" className={`side-menu ${isMenuOpen ? "show" : ""}`}>
        <button className="close-btn" onClick={toggleMenu}>x</button>
        <ul>
          {username && <li>{t('welcome')}, {username}</li>}
          <li><Link to="/feedback">{t('feedback')}</Link></li>
          <li><Link to="/adminLogin">{t('adminLogin')}</Link></li>
          <li><Link to="/password-settings">{t('passwordSettings')}</Link></li>
          <li><button onClick={handleSignOut}>{t('signOut')}</button></li>
        </ul>
      </div>
    </>
  );
};

export default Navigationbar;
