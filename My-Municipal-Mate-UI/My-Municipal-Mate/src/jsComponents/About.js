import React, { useState, useEffect } from 'react';
import { useTranslation } from 'react-i18next';
import '../components/About.css';
import logo from '../assets/images/logo2.png';


// Define the image URLs for the scrolling images
const images = [
    '/images/dustbin.jpg',
    '/images/construction.jpeg',
    '/images/streetlamp.jpeg',
    // '/images/west.jpg'
];
const truckImage = '/images/tru.png'; // Direct path to the truck image

// Define the image URLs and corresponding links for the link boxes
const BoxesImages = [
    '/images/firebrigade.jpg',
    '/images/police.jpg',
    '/images/hospital.jpg',
    '/images/library.jpg',
    '/images/electricity.avif',
    '/images/school.avif',
    '/images/park.avif',
    '/images/transit.jpg'
];

const BoxesLinks = [
    { links: 'https://www.pmc.gov.in/en/fire', para: 'Fire Brigade' },
    { links: 'https://punepolice.gov.in/', para: 'Police' },
    { links: 'https://www.pmc.gov.in/en/hosp-list', para: 'Hospital' },
    { links: 'https://www.puneonline.in/guide/libraries-in-pune', para: 'Library' },
    { links: 'https://www.pmc.gov.in/en/electrical', para: 'Electricity' },
    { links: 'https://www.pmc.gov.in/en/school-and-learning-centers', para: 'School' },
    { links: 'https://www.pmc.gov.in/en/garden', para: 'Park' },
    { links: 'https://www.pmc.gov.in/en/local-transportation-0', para: 'Transit' }
];

export default function About() {
    const [currentIndex, setCurrentIndex] = useState(0);
    const { t } = useTranslation();

    useEffect(() => {
        const interval = setInterval(() => {
            setCurrentIndex((prevIndex) => (prevIndex + 1) % images.length);
        }, 3000); // Change image every 3 seconds

        return () => clearInterval(interval); // Clean up interval on component unmount
    }, []);

    // Handle click event to open the link in a new tab
    const handleBoxClick = (url) => {
        window.open(url, '_blank');
    };

    return (
        <div className="about-container" style={{backgroundColor: 'whitesmoke'}}>
            <div className="truck-about">
                <div className="truck">
                    <img src={truckImage} alt="Truck" />
                </div>
                <div className="truck-det">
                    <div className="tagline-highlight-one">
                        <span className="tagline-highlight">{t('taglineOne')}</span>
                    </div>
                    {/* <div className="tagline-highlight-two">
                        <span className="tagline-main">
                            Connecting communities with ease – My Municipal Mate brings
                        </span>
                        <span className="tagline-main-two">
                            municipal services into the digital age.
                        </span>
                    </div> */}
                    <div className="tagline-highlight-two">
                        <span className="tagline-main">
                        {t('taglineTwo')}
                        </span>
                    </div>
                </div>
            </div>

            <div className="img-scroller-wrapper">
                <div
                    className="img-scroller"
                    style={{ transform: `translateX(-${currentIndex * 100}vw)` }}
                >
                    {images.map((src, index) => (
                        <img key={index} src={src} alt={`Images ${index + 1}`} />
                    ))}
                </div>
            </div>

            {/* <div className="about-part">
                <h1>Welcome to</h1>
                <h2>My Municipal Mate</h2>
                <p>
                    Nagpur Municipal Corporation was initially governed by the City of Nagpur Corporation Act (CNC Act) 1948, till the implementation of Maharashtra Municipal Corporation Act. The CNC Act was repealed in 2012 vide Maharashtra Notification No. 23 Dated 21 August 2012 and thereafter the working of Nagpur Municipal Corporation is governed by Maharashtra Municipal Corporation Act.
                </p>
                <p>
                    The key responsibility for providing basic urban services to the citizens of Nagpur lies with the Nagpur Municipal Corporation. These services include water supply, sewerage, waste management, slum improvement, land use planning, construction and maintenance of roads, street lighting, maintenance of parks and gardens, providing primary health and education facilities, etc.
                </p>
                <p>
                    The Municipal Commissioner is the administrative head of the Municipal Corporation who is assisted by Additional Municipal Commissioner, Deputy Municipal Commissioner, Asst. Municipal Commissioner, and various departmental Heads. NMC coordinates with various other government organizations like NIT, MHADA, MSRTC, the Traffic Police, MPCB, etc., for delivering these basic urban services.
                </p>
            </div> */}

            <div className="about-part">
                <h1>{t('welcome')}</h1>
                <h2>{t('title')}</h2>
                
                    <p>{t('aboutUs.p1')}</p>
                    <p>{t('aboutUs.p2')}</p>
                    <p>{t('aboutUs.p3')}</p>
                    <p>{t('aboutUs.p4')}</p>
                    <div>
                        <img src= {logo} style={{height:'200px',width:'300px', marginTop:'50px'} } alt='error'></img>
                    </div>

            </div>

            <div className="link-boxes">
                {/* <div className="box-heading">Quick Links</div> */}
                <div className="box-heading">{t('quickLinks')}</div>
                <div className="boxes-container">
                    {BoxesImages.map((src, index) => (
                        <div
                            className="box"
                            key={index}
                            onClick={() => handleBoxClick(BoxesLinks[index].links)}
                        >
                            <img src={src} alt={`Box ${index + 1}`} />
                            <p>{BoxesLinks[index].para}</p>
                        </div>
                    ))}
                </div>
            </div>
            <div className="contact-container">
  <div className="contact-section">
    <h2>Follow Us On</h2>
    <div className="social-icons">
      <a href="https://twitter.com" className="icon twitter" aria-label="Twitter">
        <i className="fab fa-twitter"></i>
      </a>
      <a href="https://instagram.com" className="icon instagram" aria-label="Instagram">
        <i className="fab fa-instagram"></i>
      </a>
      <a href="https://github.com" className="icon github" aria-label="GitHub">
        <i className="fab fa-github"></i>
      </a>
    </div>
  </div>

  <hr className="divider" />

  <div className="contact-section">
    {/* <p>For any site feedback and other review, use our social media handles or email us. We would like to hear from you!!!</p> */}
    <p>{t('contact.feedback')}</p>
    
  </div>

  <hr className="divider" />

  <div className="contact-section">
    {/* <h2>Contact Us</h2> */}
    {/* <address>
      <p>ACTS-CDAC, Panchavati, Pashan- Pune</p>
      <p>Email: <a href="mymunicipalmate.com">mymunicipalmate.com</a></p>
      <p>Phone: <a href="tel:+1234567890">+123 456 7890</a></p>
    </address> */}
    <h2>{t('contact.heading')}</h2>
    <address>
    <p>{t('contact.address')}</p>
    <p>{t('contact.email')}</p>
    <p>{t('contact.phone')}</p>
    </address>
    
  </div>
</div>

<div className="footer-container">
  {/* <p>&copy; 2024 My Municipal Mate. All rights reserved.</p> */}
  <p>{t('footer.copyright')}</p>
</div>

 

  
        </div>
    );
}
