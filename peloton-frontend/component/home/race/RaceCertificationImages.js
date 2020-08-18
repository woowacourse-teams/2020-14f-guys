import React from "react";
import { StyleSheet } from "react-native";
import Swiper from "react-native-swiper";
import RaceCertificationImage from "./RaceCertificationImage";
import { useRecoilValue } from "recoil/dist";
import { certificationState } from "../../../state/certification/CertificationState";

const RaceCertificationImages = () => {
  const certifications = useRecoilValue(certificationState);

  return (
    <Swiper
      style={styles.container}
      loop={true}
      showsPagination={true}
      autoplay
      autoplayTimeout={2.5}
      key={certifications ? certifications.length : 1}
    >
      {certifications && certifications.length > 0 ? (
        certifications.map((item, index) => (
          <RaceCertificationImage key={index} item={item} touchable={true} />
        ))
      ) : (
        <RaceCertificationImage key={0} item={null} touchable={false} />
      )}
    </Swiper>
  );
};

const styles = StyleSheet.create({
  container: {
    height: 600,
  },
});

export default RaceCertificationImages;
