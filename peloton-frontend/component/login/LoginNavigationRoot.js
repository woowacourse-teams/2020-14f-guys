import React from "react";
import { StyleSheet } from "react-native";
import { createStackNavigator } from "@react-navigation/stack";
import ChangeProfile from "./ChangeProfile";
import Login from "./Login";
import ApplicationNavigationRoot from "../ApplicationNavigationRoot";

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
        name="ChangeNickname"
        component={ChangeProfile}
        options={{ headerShown: false }}
      />
      <LoginStack.Screen
        name="ApplicationNavigationRoot"
        component={ApplicationNavigationRoot}
        options={{ headerShown: false }}
      />
    </LoginStack.Navigator>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
});

export default LoginNavigationRoot;
