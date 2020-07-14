import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import HomeMain from "./HomeMain";
import RaceDetail from "./race/RaceDetail";
import ShareButton from "./race/ShareButton";

const Stack = createStackNavigator();

const Home = () => {
  return (
    <Stack.Navigator initialRouteName="RaceDetail">
      <Stack.Screen
        name="HomeMain"
        component={HomeMain}
        options={{
          title: "Peloton",
        }}
      />
      <Stack.Screen
        name="RaceDetail"
        component={RaceDetail}
        options={{
          title: "진행중인 레이스",
          headerRight: () => <ShareButton />,
        }}
      />
    </Stack.Navigator>
  );
};

export default Home;
