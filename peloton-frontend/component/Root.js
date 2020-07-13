import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import Home from "./home/Home";
import Certification from "./certification/Certification";
import Profile from "./profile/Profile";

const Tab = createBottomTabNavigator();

const Root = () => {
  return (
    <Tab.Navigator>
      <Tab.Screen name="Home" component={Home} />
      <Tab.Screen name="Certification" component={Certification} />
      <Tab.Screen name="Profile" component={Profile} />
    </Tab.Navigator>
  );
};

export default Root;
