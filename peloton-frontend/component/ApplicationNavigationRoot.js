import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import HomeNavigationRoot from "./HomeNavigationRoot";
import Certification from "./certification/Certification";
import { MaterialIcons } from "@expo/vector-icons";
import { View } from "react-native";
import { COLOR } from "../utils/constants";
import ProfileNavigationRoot from "./ProfileStackRoot";

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
      <View
        style={{
          paddingTop: 20,
          paddingHorizontal: 20,
        }}
      >
        <MaterialIcons name={iconName} size={25} color={color} />
      </View>
    );
  },
});

const tabBarOptions = {
  showLabel: false,
  activeTintColor: COLOR.LAVENDER,
  inactiveTintColor: COLOR.BLUE1,
};

const ApplicationNavigationRoot = () => {
  return (
    <Tab.Navigator
      screenOptions={screenOptions}
      tabBarOptions={tabBarOptions}
      initialRouteName="Home"
    >
      <Tab.Screen name="Home" component={HomeNavigationRoot} />
      <Tab.Screen name="Certification" component={Certification} />
      <Tab.Screen name="Profile" component={ProfileNavigationRoot} />
    </Tab.Navigator>
  );
};

export default ApplicationNavigationRoot;
