import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import Profile from "./Profile";
import WebViewScreen from "./WebViewScreen";

const Stack = createStackNavigator();

const ProfileScreen = () => {
  return (
    <Stack.Navigator initialRouteName="Profile">
      <Stack.Screen name="Profile" component={Profile} />
      <Stack.Screen name="WebScreen" component={WebViewScreen} />
    </Stack.Navigator>
  );
};

export default ProfileScreen;
