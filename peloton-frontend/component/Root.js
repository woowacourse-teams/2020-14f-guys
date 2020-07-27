import React from "react";
import { createBottomTabNavigator } from "@react-navigation/bottom-tabs";
import Home from "./home/Home";
import Certification from "./certification/Certification";
import { MaterialIcons } from "@expo/vector-icons";
import { View } from "react-native";
import { COLOR } from "../utils/constants";
import ProfileScreen from "./profile/ProfileScreen";
import { RecoilRoot } from "recoil/dist";

const Tab = createBottomTabNavigator();

const screenOptions = ({ route }) => ({
  tabBarIcon: ({ focused, color, size }) => {
    let iconName;

    if (route.name === "Home") {
      iconName = "home";
    } else if (route.name === "Certification") {
      iconName = "camera";
    } else if (route.name === "ProfileScreen") {
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
  inactiveTintColor: COLOR.BLUE,
};

const Root = () => {
  return (
    <RecoilRoot>
      <Tab.Navigator
        screenOptions={screenOptions}
        tabBarOptions={tabBarOptions}
        initialRouteName="Home"
      >
        <Tab.Screen name="Home" component={Home} />
        <Tab.Screen name="Certification" component={Certification} />
        <Tab.Screen name="ProfileScreen" component={ProfileScreen} />
      </Tab.Navigator>
    </RecoilRoot>
  );
};

export default Root;
