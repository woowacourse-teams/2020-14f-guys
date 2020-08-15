import React, { useState } from "react";
import {
  Dimensions,
  Image,
  StyleSheet,
  Text,
  TouchableOpacity,
  View,
} from "react-native";
import { useRecoilValue, useSetRecoilState } from "recoil";
import { Feather } from "@expo/vector-icons";
import * as ImagePicker from "expo-image-picker";
import { useLinkTo } from "@react-navigation/native";

import { raceCertificationState } from "../../state/certification/RaceCertificationState";
import { COLOR } from "../../utils/constants";
import { loadingState } from "../../state/loading/LoadingState";
import {
  getCameraPermission,
  getCameraRollPermission,
} from "../../utils/Permission";
import PaymentButton from "../home/race/PaymentButton";
import { CertificationApi } from "../../utils/api/CertificationApi";
import { memberTokenState } from "../../state/member/MemberState";
import LoadingIndicator from "../../utils/LoadingIndicator";

const CertificationSubmit = ({ route }) => {
  const raceCertifications = useRecoilValue(raceCertificationState);
  const setIsLoading = useSetRecoilState(loadingState);
  const token = useRecoilValue(memberTokenState);
  const linkTo = useLinkTo();

  const { index } = route.params;
  const raceCertification = raceCertifications[index];
  const [photoUri, setPhotoUri] = useState(
    raceCertification.race.certification_example,
  );

  const submitCertification = async () => {
    setIsLoading(true);
    const formData = new FormData();
    formData.append("certification_image", {
      uri: photoUri,
      type: "image/jpeg",
      name: photoUri.substring(9),
    });
    formData.append("status", "SUCCESS");
    formData.append("description", "필요없을 수도 있는 설명");
    formData.append("riderId", raceCertification.rider_id);
    formData.append("missionId", raceCertification.mission.id);
    const { location } = await CertificationApi.post(token, formData);
    setIsLoading(false);
    if (!location) {
      alert("문제가 발생했습니다. 잠시 후 다시 시도해주세요");
      return;
    }
    alert("인증 완료되었습니다");
    linkTo(`/home/races/detail/${raceCertification.race.id}`);
  };

  const takePhoto = async () => {
    setIsLoading(true);
    const hasCameraPermission = await getCameraPermission();
    const hasCameraRollPermission = await getCameraRollPermission();
    if (hasCameraPermission === false || hasCameraRollPermission === false) {
      setIsLoading(false);
      return;
    }
    const pickerResult = await ImagePicker.launchCameraAsync({
      allowsEditing: true,
      aspect: [4, 3],
      base64: true,
    });

    if (pickerResult.cancelled === true) {
      setIsLoading(false);
      return;
    }
    const selectedImage = pickerResult.uri;
    setPhotoUri(selectedImage);
    setIsLoading(false);
  };

  return (
    <LoadingIndicator>
      <View style={styles.container}>
        <TouchableOpacity
          style={styles.certificationButton}
          onPress={takePhoto}
        >
          <Image
            source={{ uri: photoUri }}
            style={styles.certificationExample}
          />
          <View style={styles.certificationPhoto}>
            <Feather name="camera" size={50} color="white" />
          </View>
        </TouchableOpacity>
        <View style={styles.instructionContainer}>
          <Text style={styles.instruction}>
            {raceCertification.mission.mission_instruction}
          </Text>
        </View>
        {photoUri !== raceCertification.race.certification_example && (
          <View style={styles.absoluteBottom}>
            <PaymentButton onPress={submitCertification} />
          </View>
        )}
      </View>
    </LoadingIndicator>
  );
};

const { width } = Dimensions.get("window");

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
  },
  certificationExample: {
    width,
    aspectRatio: 0.8,
    resizeMode: "cover",
  },
  instructionContainer: {
    marginTop: 20,
  },
  instruction: {
    fontSize: 20,
    fontWeight: "300",
    fontStyle: "normal",
    lineHeight: 35,
    color: COLOR.GRAY1,
    textAlign: "center",
  },
  certificationButton: {
    width,
  },
  certificationPhoto: {
    position: "absolute",
    left: 0,
    right: 0,
    bottom: 0,
    top: 0,
    justifyContent: "center",
    alignItems: "center",
    opacity: 0.7,
  },
  absoluteBottom: {
    position: "absolute",
    bottom: 0,
  },
});

export default CertificationSubmit;
