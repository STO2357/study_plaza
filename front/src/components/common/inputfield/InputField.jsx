import React from "react";
import styles from "./InputField.module.css";
import clsx from "clsx";

const InputField = ({
  label,
  type = "text",
  value,
  onChange,
  error,
  placeholder,
  size = "medium", // small, medium, large
  variant = "default", // default, outline, filled
  disabled = false,
}) => {
  return (
    <div className={styles.wrapper}>
      {label && <label className={styles.label}>{label}</label>}
      <input
        type={type}
        value={value ?? ""}
        onChange={onChange}
        placeholder={placeholder}
        className={clsx(
          styles.input,
          styles[variant],
          styles[size],
          error && styles.error
        )}
        disabled={disabled}
      />
      {error && <p className={styles.errorMsg}>{error}</p>}
    </div>
  );
};

export default InputField;