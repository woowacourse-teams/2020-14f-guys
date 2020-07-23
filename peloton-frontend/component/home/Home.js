import React from "react";
import { createStackNavigator } from "@react-navigation/stack";

import HomeMain from "./homemain/HomeMain";
import RaceDetail from "./race/RaceDetail";
import CategorySelection from "./racecreate/CategorySelection";
import ShareButton from "./race/ShareButton";
import InputRaceInfo from "./racecreate/InputRaceInfo";

const Stack = createStackNavigator();

const Home = () => {
  return (
    <Stack.Navigator initialRouteName="HomeMain">
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
      <Stack.Screen
        name="CategorySelection"
        component={CategorySelection}
        options={{ title: "카테고리 선택" }}
      />
      <Stack.Screen
        name="InputRaceInfo"
        component={InputRaceInfo}
        options={{ title: "레이스 정보 입력" }}
      />
    </Stack.Navigator>
  );
};

export default Home;
