import React from "react";
import { Link } from "react-router-dom";
import styles from "./layoutStyle/Header.module.css";

const Header = () => {
    const token = localStorage.getItem("accessToken");

  return (
    <header className={styles.header}>
      <div className={styles.logo}>
        <Link to="/" className={styles.logoLink}>
          Study Plaza
        </Link>
      </div>
      
      <nav>
          <ul className={styles.navList}>
              <li><Link to="/">홈</Link></li>
              {!token ? (
                  <>
                      <li><Link to="/login">로그인</Link></li>
                      <li><Link to="/signup">회원가입</Link></li>
                  </>
              ) : (
                  <li><Link to="/logout">로그아웃</Link></li>
              )}
          </ul>
      </nav>
    </header>
  );
};

export default Header;
