import axiosInstance from "./axiosInstance";

const USE_MOCK = false;

export async function signupApi(userData) {
  if (USE_MOCK) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (userData.userId === "asdf1234") {
          reject({
            response: { data: { message: "이미 존재하는 아이디입니다." } },
          });
        } else {
          resolve({
            data: { status: "success", data: "회원가입 완료" },
          });
        }
      }, 500);
    });
  }
  return axiosInstance.post("/signup", userData);
}

export async function loginApi(credentials) {
  if (USE_MOCK) {
    return new Promise((resolve, reject) => {
      setTimeout(() => {
        if (credentials.userId === "asdf1234" && credentials.password === "1234") {
          resolve({ token: "mock-jwt-token" });
        } else {
          reject({ message: "아이디 또는 비밀번호가 잘못되었습니다." });
        }
      }, 500);
    });
  }
  return axiosInstance.post("/login", credentials);
}