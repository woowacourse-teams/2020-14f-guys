import React from "react";
import { createStackNavigator } from "@react-navigation/stack";
import ProfileDetail from "./profile/profiledetail/ProfileDetail";
import ProfileEdit from "./profile/profileedit/ProfileEdit";
import CashUpdate from "./profile/profileedit/CashUpdate";
import NameUpdate from "./profile/profileedit/NameUpdate";

const ProfileStack = createStackNavigator();

const ProfileStackRoot = () => {
  return (
    <ProfileStack.Navigator initialRouteName="ProfileDetail">
      <ProfileStack.Screen
        name="ProfileDetail"
        component={ProfileDetail}
        options={{
          title: "Peloton",
        }}
      />
      <ProfileStack.Screen
        name="ProfileEdit"
        component={ProfileEdit}
        options={{
          title: "Settings",
        }}
      />
      <ProfileStack.Screen
        name="CashUpdate"
        component={CashUpdate}
        options={{
          title: "충전하기",
        }}
      />
      <ProfileStack.Screen
        name="NameUpdate"
        component={NameUpdate}
        options={{
          title: "닉네임 변경",
        }}
      />
    </ProfileStack.Navigator>
  );
};

export default ProfileStackRoot;
