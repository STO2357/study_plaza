import React, { useState } from "react";
import InputField from "../../components/common/inputfield/InputField";
import Button from "../../components/common/button/Button";
import { loginApi } from "../../api/auth";

import styles from "./authStyle/Login.module.css";

const Login = () => {
  const [userId, setMemberId] = useState("");
  const [password, setPassword] = useState("");

   const handleLogin = async () => {
    try {
      const res = await loginApi({ userId, password });
      console.log(res.token);
    } catch (err) {
      console.error(err.message);
    }
  };

  return (
    <div className={styles.container}>
      <h1>로그인</h1>
      <InputField
        label="아이디"
        type="text"
        value={userId}
        onChange={(e) => setMemberId(e.target.value)}
        placeholder="아이디를 입력하세요"
      />
      <InputField
        label="비밀번호"
        type="password"
        value={password}
        onChange={(e) => setPassword(e.target.value)}
        placeholder="비밀번호를 입력하세요"
      />
      <Button variant="primary" size="medium" onClick={handleLogin}>
        로그인
      </Button>
    </div>
  );
};

export default Login;
