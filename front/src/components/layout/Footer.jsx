import React from "react";
import styles from "./layoutStyle/Footer.module.css";

const Footer = () => {
  return (
    <footer className={styles.footer}>
      <p>© {new Date().getFullYear()} StudyPlaza. All rights reserved.</p>
    </footer>
  );
};

export default Footer;
