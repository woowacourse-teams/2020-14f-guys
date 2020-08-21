import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import { MaterialIcons } from "@expo/vector-icons";
import { View, Platform } from "react-native";

import Certification from "./certification/Certification";
import { COLOR } from "../utils/constants";
import ProfileNavigationRoot from "./ProfileStackRoot";
import CertificationNavigationRoot from "./certification/CertificationNavigationRoot";
import HomeNavigationRoot from "./home/HomeNavigationRoot";

const Tab = createBottomTabNavigator();

const screenOptions = ({ route }) => ({
  tabBarIcon: ({ focused, color, size }) => {
    let iconName;

    if (route.name === "Home") {
      iconName = "home";
    } else if (route.name === "Certification") {
      iconName = "camera";
    } else if (route.name === "Profile") {
      iconName = "person";
    }
    return (
      <View style={Platform.OS === "ios" ? { marginTop: 25 } : null}>
        <MaterialIcons name={iconName} size={25} color={color} />
      </View>
    );
  },
});

const tabBarOptions = {
  showLabel: false,
  activeTintColor: COLOR.LAVENDER,
  inactiveTintColor: COLOR.BLUE1,
  style: {
    justifyContent: "center",
    alignItems: "center",
  },
};

const ApplicationNavigationRoot = () => {
  return (
    <Tab.Navigator
      screenOptions={screenOptions}
      tabBarOptions={tabBarOptions}
      initialRouteName="Home"
    >
      <Tab.Screen name="Home" component={HomeNavigationRoot} />
      <Tab.Screen
        name="Certification"
        component={CertificationNavigationRoot}
      />
      <Tab.Screen name="Profile" component={ProfileNavigationRoot} />
    </Tab.Navigator>
  );
};

export default ApplicationNavigationRoot;
