import React from "react";
import { Link } from "react-router-dom";
import styles from "./layoutStyle/Sidebar.module.css";

const Sidebar = () => {
  return (
    <aside className={styles.sidebar}>
      <ul>
        <li><Link to="/function1">기능 1</Link></li>
        <li><Link to="/function2">기능 2</Link></li>
        <li><Link to="/function3">기능 3</Link></li>
      </ul>
    </aside>
  );
};

export default Sidebar;
