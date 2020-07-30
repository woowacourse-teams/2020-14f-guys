import React, { useState } from "react";
import { Image, StyleSheet, Text, TextInput, TouchableOpacity, View, } from "react-native";
import Axios from "axios";
import { useRecoilValue } from "recoil";
import { userInfoState, userTokenState } from "../../atoms";
import { useNavigation } from "@react-navigation/core";
import { SERVER_BASE_URL } from "../../../utils/constants";
import { useSetRecoilState } from "recoil/dist";

const ProfileEditInfo = () => {
  const userInfo = useRecoilValue(userInfoState);
  const setUserInfo = useSetRecoilState(userInfoState);
  const token = useRecoilValue(userTokenState);
  const navigation = useNavigation();

  const [name, setName] = useState(userInfo.name);

  const requestChangeName = () => {
    Axios.patch(
      `${SERVER_BASE_URL}/api/members/name`,
      {
        name: name,
      },
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    )
      .then(async () => {
        const response = await Axios.get(`${SERVER_BASE_URL}/api/members`, {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        await setUserInfo(response.data);
        navigation.navigate("ProfileDetail");
      })
      .catch((error) => console.log(error));
  };

  return (
    <View style={styles.infoContainer}>
      {/*1번*/}
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>🚴 ‍️Rider Name</Text>
          <TextInput
            style={styles.eachInfoValue}
            onChangeText={(text) => setName(text)}
            value={name}
          />
        </View>
        <TouchableOpacity style={styles.button} onPress={requestChangeName}>
          <Text style={styles.buttonText}>수정하기</Text>
          <Image
            style={styles.buttonImage}
            source={require("../../../assets/icn_back_dark.png")}
          />
        </TouchableOpacity>
      </View>
      {/*2번*/}
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>😀 Kakao ID</Text>
          <Text style={styles.eachInfoValue}>{userInfo.kakaoId}</Text>
        </View>
      </View>
      {/*3번*/}
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>💰 Cash</Text>
          <Text style={styles.eachInfoValue}>{userInfo.cash}</Text>
        </View>
        <TouchableOpacity
          style={styles.button}
          onPress={() => navigation.navigate("CashUpdate")}
        >
          <Text style={styles.buttonText}>충전하기</Text>
          <Image
            style={styles.buttonImage}
            source={require("../../../assets/icn_back_dark.png")}
          />
        </TouchableOpacity>
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  infoContainer: {
    flex: 2,
    height: 300,
    alignItems: "center",
    marginHorizontal: 18,
    justifyContent: "space-around",
    backgroundColor: "white",
    borderWidth: 0,
    borderColor: "#F0F3F4",
    borderRadius: 20,
    shadowColor: "rgba(80,92,98,0.04)",
    shadowOffset: {
      width: 0,
      height: 5,
    },
    shadowRadius: 10,
    shadowOpacity: 1,
  },
  editIcon: {
    color: "black",
  },
  eachInfo: {
    flex: 1,
    width: "100%",
    flexDirection: "row",
    borderWidth: 1,
    borderColor: "#F0F3F4",
  },
  eachTextBox: {
    flex: 5,
    justifyContent: "center",
    paddingLeft: 25,
  },
  editButton: {
    flex: 1,
    justifyContent: "center",
    alignItems: "flex-end",
    paddingRight: 25,
  },
  button: {
    flex: 2,
    flexDirection: "row",
    justifyContent: "flex-end",
    alignItems: "center",
    paddingRight: 25,
  },
  buttonText: {
    color: "#6e8ca0",
    paddingRight: 5,
  },
  eachInfoKey: {
    fontSize: 17,
    fontWeight: "800",
    color: "#334856",
    lineHeight: 35,
    paddingBottom: 5,
  },
  eachInfoValue: {
    color: "#6e8ca0",
    marginLeft: 26,
    marginRight: 60,
    paddingBottom: 7,
    borderBottomColor: "#eceff0",
    borderBottomWidth: 1,
  },
  editButtonImage: {
    width: 20,
    height: 20,
  },
  buttonImage: {
    width: 10,
    height: 10,
  },
});

export default ProfileEditInfo;
