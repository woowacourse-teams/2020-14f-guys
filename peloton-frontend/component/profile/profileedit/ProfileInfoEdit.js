import React from "react";
import { StyleSheet, Text, TouchableOpacity, View } from "react-native";
import { useRecoilState } from "recoil";
import { useNavigation } from "@react-navigation/core";
import { memberInfoState } from "../../../state/member/MemberState";
import { AntDesign } from "@expo/vector-icons";
import { COLOR } from "../../../utils/constants";

const ProfileEditInfo = () => {
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const navigation = useNavigation();

  return (
    <View style={styles.infoContainer}>
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>🚴 ‍️Rider Name</Text>
          <Text style={styles.eachInfoValue}>{memberInfo.name}</Text>
        </View>
        <TouchableOpacity
          style={styles.button}
          onPress={() => navigation.navigate("NameUpdate")}
        >
          <Text style={styles.buttonText}>수정하기</Text>
          <AntDesign name="right" size={18} color={COLOR.GREEN2} />
        </TouchableOpacity>
      </View>
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>😀 Kakao ID</Text>
          <Text style={styles.eachInfoValue}>{memberInfo.kakao_id}</Text>
        </View>
      </View>
      <View style={styles.eachInfo}>
        <View style={styles.eachTextBox}>
          <Text style={styles.eachInfoKey}>💰 Cash</Text>
          <Text style={styles.eachInfoValue}>{memberInfo.cash}</Text>
        </View>
        <TouchableOpacity
          style={styles.button}
          onPress={() => navigation.navigate("CashUpdate")}
        >
          <Text style={styles.buttonText}>충전하기</Text>
          <AntDesign name="right" size={18} color={COLOR.GREEN2} />
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
    backgroundColor: COLOR.WHITE,
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
  eachInfo: {
    flex: 1,
    width: "100%",
    flexDirection: "row",
    borderWidth: 1,
    borderColor: COLOR.WHITE3,
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
    color: COLOR.GREEN2,
    paddingRight: 5,
  },
  eachInfoKey: {
    fontSize: 17,
    fontWeight: "800",
    color: COLOR.GREEN3,
    lineHeight: 35,
    paddingBottom: 5,
  },
  eachInfoValue: {
    color: COLOR.GREEN2,
    marginLeft: 26,
    marginRight: 60,
    paddingBottom: 7,
    borderBottomColor: COLOR.WHITE4,
    borderBottomWidth: 1,
    fontSize: 17,
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
