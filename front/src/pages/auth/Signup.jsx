import React, { useState } from "react";

import InputField from "../../components/common/inputfield/InputField";
import Button from "../../components/common/button/Button";
import { signupApi } from "../../api/auth";

import styles from "./authStyle/Signup.module.css";

const Signup = () => {
  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
  });

  const handleChange = (field) => (e) => {
    setForm((prev) => ({ ...prev, [field]: e.target.value }));
  };

  const handleSignup = async () => {
  try {
    const res = await signupApi(form);
    console.log(res.message);
  } catch (err) {
    console.error(err.message);
  }
};

  return (
    <div className={styles.container}>
      <h1>회원가입</h1>
      <InputField
        label="아이디"
        value={form.userId}
        onChange={handleChange("userId")}
        placeholder="사용할 아이디를 입력하세요"
      />
      <InputField
        label="이름"
        type="name"
        value={form.name}
        onChange={handleChange("name")}
        placeholder="이름을 입력하세요"
      />
      <InputField
        label="비밀번호"
        type="password"
        value={form.password}
        onChange={handleChange("password")}
        placeholder="비밀번호를 입력하세요"
      />
      <Button variant="primary" size="medium" onClick={handleSignup}>
        회원가입
      </Button>
    </div>
  );
};

export default Signup;
