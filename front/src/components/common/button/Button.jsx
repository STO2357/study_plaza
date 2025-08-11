import React from "react";
import styles from "./Button.module.css";
import clsx from "clsx"; 

const Button = ({
  children,
  onClick,
  type = "button",      
  variant = "primary",  
  size = "medium",  
  disabled = false,
}) => {
  return (
    <button
      type={type}
      onClick={onClick}
      className={clsx(styles.button, styles[variant], styles[size])}
      disabled={disabled}
    >
      {children}
    </button>
  );
};

export default Button;