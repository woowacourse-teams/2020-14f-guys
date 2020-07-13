import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import HomeMain from "./HomeMain";
import RaceDetail from "./RaceDetail";

const Stack = createStackNavigator();

const Home = () => {
  return (
    <Stack.Navigator>
      <Stack.Screen
        name="HomeMain"
        component={HomeMain}
        options={{ title: "Peloton" }}
      />
      <Stack.Screen name="RaceDetail" component={RaceDetail} />
    </Stack.Navigator>
  );
};

export default Home;
