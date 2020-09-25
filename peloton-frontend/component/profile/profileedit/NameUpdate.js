import React, { useState } from "react";
import {
  Alert,
  Keyboard,
  StyleSheet,
  Text,
  TextInput,
  TouchableOpacity,
  TouchableWithoutFeedback,
  View,
} from "react-native";
import { COLOR } from "../../../utils/constants";
import { useRecoilState, useRecoilValue, useSetRecoilState } from "recoil";
import {
  memberInfoState,
  memberTokenState,
} from "../../../state/member/MemberState";
import { useNavigation } from "@react-navigation/core";
import { MemberApi } from "../../../utils/api/MemberApi";
import { loadingState } from "../../../state/loading/LoadingState";

const NameUpdate = () => {
  const token = useRecoilValue(memberTokenState);
  const navigation = useNavigation();
  const [memberInfo, setMemberInfo] = useRecoilState(memberInfoState);
  const [name, setName] = useState(memberInfo.name);
  const setIsLoading = useSetRecoilState(loadingState);

  const requestChangeName = async () => {
    setIsLoading(true);
    try {
      await MemberApi.patchName(token, name);
      const newMemberInfo = await MemberApi.get(token);
      setMemberInfo(newMemberInfo);
      navigation.navigate("ProfileEdit");
    } catch (error) {
      Alert.alert("", error.response.data.code);
      console.log(error.response.data.message);
    }
    setIsLoading(false);
  };

  return (
    <TouchableWithoutFeedback onPress={() => Keyboard.dismiss()}>
      <View style={styles.container}>
        <View style={styles.infoContainer}>
          <View style={{ width: "100%" }}>
            <Text style={styles.infoText}>변경할 닉네임을 입력해주세요</Text>
            <TextInput
              style={styles.infoInput}
              onChangeText={(text) => setName(text)}
              value={name}
            />
          </View>
        </View>
        <View style={styles.buttonContainer}>
          <TouchableOpacity style={styles.button} onPress={requestChangeName}>
            <Text style={styles.buttonText}>변경하기</Text>
          </TouchableOpacity>
        </View>
      </View>
    </TouchableWithoutFeedback>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  infoContainer: {
    flex: 5,
    alignItems: "center",
    justifyContent: "center",
  },
  infoText: {
    marginHorizontal: "14%",
    color: COLOR.GREEN3,
    fontWeight: "bold",
    fontSize: 20,
    letterSpacing: -0.36,
    marginBottom: 20,
  },
  infoInput: {
    backgroundColor: COLOR.WHITE,
    fontSize: 17,
    fontWeight: "300",
    color: COLOR.GREEN2,
    paddingLeft: "10%",
    height: 60,
    width: "90%",
    shadowColor: "rgba(27, 28, 32, 0.05)",
    shadowOffset: {
      width: 0,
      height: 10,
    },
    shadowRadius: 10,
    shadowOpacity: 0.1,
    borderRadius: 10,
    alignSelf: "center",
  },
  buttonContainer: {
    flex: 2,
    alignItems: "center",
  },
  button: {
    marginTop: 50,
    width: 150,
    height: 50,
    borderRadius: 100,
    backgroundColor: COLOR.WHITE,
    color: COLOR.BLACK2,
    alignItems: "center",
    justifyContent: "center",

    shadowColor: "rgba(0, 0, 0, 0.05)",
    shadowOffset: {
      width: 0,
      height: 10,
    },
    shadowRadius: 10,
    shadowOpacity: 0.1,
  },
  buttonText: {
    color: COLOR.GREEN3,
    fontSize: 14,
  },
});

export default NameUpdate;
