import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import Profile from "./Profile";

const Stack = createStackNavigator();

const ProfileStackRoot = () => {
  return (
    <Stack.Navigator initialRouteName="Profile">
      <Stack.Screen name="Profile" component={Profile} />
    </Stack.Navigator>
  );
};

export default ProfileStackRoot;
