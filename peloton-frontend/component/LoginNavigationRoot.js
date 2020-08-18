import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import ChangeProfile from "./login/ChangeProfile";
import Login from "./login/Login";
import ApplicationNavigationRoot from "./ApplicationNavigationRoot";
import GoBackButton from "./home/race/GoBackButton";

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
        options={{ headerShown: false}}
      />
      <LoginStack.Screen
        name="ApplicationNavigationRoot"
        component={ApplicationNavigationRoot}
        options={{ headerShown: false }}
      />
    </LoginStack.Navigator>
  );
};

export default LoginNavigationRoot;
