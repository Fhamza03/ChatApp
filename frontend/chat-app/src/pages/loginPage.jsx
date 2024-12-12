import React, { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import ChatHub from "../assets/ChatHub.png";
import LoginImage from "../assets/LoginImage.jpg";

const LoginPage = () => {
  const [formData, setFormData] = useState({ userName: "", password: "" });
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();

  const handleLogin = async (e) => {
    e.preventDefault();

    const { userName, password } = formData;

    try {
      const response = await fetch("http://localhost:8080/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({ userName, password }),
      });

      if (response.ok) {
        const responseData = await response.json();
        const userId = responseData.userId;

        sessionStorage.setItem("userId", userId);
        sessionStorage.setItem("username", userName);

        navigate("/chat");
      } else {
        const errorText = await response.text();
        setErrorMessage(errorText || "Login failed");
      }
    } catch (error) {
      setErrorMessage("Error: " + error.message);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prevData) => ({
      ...prevData,
      [name]: value,
    }));
  };

  return (
    <div className="flex h-screen">
      <div className="hidden lg:flex items-center justify-center flex-1 bg-white text-black">
        <img className="w-full h-full object-cover" src={LoginImage} alt="" />
      </div>
      <div className="w-full bg-slate-200 lg:w-1/2 flex items-center justify-center bg-opacity-20 backdrop-filter backdrop-blur-xl">
        <div className="w-full lg:w-2/3 flex items-center justify-center">
          <div className="max-w-md w-full p-6 rounded-lg border border-sky-500 bg-white shadow-2xl">
            <div className="flex items-center justify-center mb-3 mt-6">
              <img
                className="w-32 h-auto lg:w-33 lg:h-auto"
                src={ChatHub}
                alt="Logo"
              />
              <div className="mx-4 border-l-4 border-gray-400 dark:border-gray-600 h-20"></div>
              <span className="text-dark dark:text-gray-400 text-3xl lg:text-4xl font-bold">
                Welcome back
              </span>
            </div>

            <form onSubmit={handleLogin} className="space-y-4 mb-8">
              <div className="flex flex-col space-y-4">
                <input
                  type="text"
                  className="border border-gray-300 p-2 rounded-lg outline-none focus:border-indigo-500"
                  placeholder="Username"
                  name="userName"
                  value={formData.userName}
                  onChange={handleChange}
                />
                <input
                  type="password"
                  className="border border-gray-300 p-2 rounded-lg outline-none focus:border-indigo-500"
                  placeholder="Password"
                  name="password"
                  value={formData.password}
                  onChange={handleChange}
                />
                <button className="bg-sky-600 hover:bg-sky-800 text-white font-semibold py-2 rounded-lg">
                  Login
                </button>
                <p className="ml-2">
                  Don't have an account?{" "}
                  <Link className="text-sky-600 hover:underline" to="/signup">
                    Sign up here
                  </Link>
                </p>
              </div>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
