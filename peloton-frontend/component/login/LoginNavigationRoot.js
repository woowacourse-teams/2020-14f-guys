import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import ChangeProfile from "./ChangeProfile";
import Login from "./Login";
import ApplicationNavigationRoot from "../ApplicationNavigationRoot";
import ErrorPage from "../ErrorPage";

const LoginStack = createStackNavigator();

const LoginNavigationRoot = () => {
  return (
    <LoginStack.Navigator initialRouteName="Login">
      <LoginStack.Screen
        name="Login"
        component={Login}
        options={{ headerShown: false }}
      />
      <LoginStack.Screen
        name="ChangeProfile"
        component={ChangeProfile}
        options={{ headerShown: false }}
      />
      <LoginStack.Screen
        name="ApplicationNavigationRoot"
        component={ApplicationNavigationRoot}
        options={{ headerShown: false }}
      />
      <LoginStack.Screen
        name="ErrorPage"
        component={ErrorPage}
        options={{ title: "에러😭" }}
      />
    </LoginStack.Navigator>
  );
};

export default LoginNavigationRoot;
