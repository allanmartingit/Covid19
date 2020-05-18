package com.example.covid19

import android.os.Parcel
import android.os.Parcelable

class Boletim(
  var suspeitos: Int=0,
  var confirmados: Int=0,
  var descartados: Int=0,
  var monitoramento: Int=0,
  var curados: Int=0,
  var mortes: Int=0,
  var data: String?,
  var hora: String?
) : Parcelable{
  constructor(parcel: Parcel) : this(
    parcel.readInt(),
    parcel.readInt(),
    parcel.readInt(),
    parcel.readInt(),
    parcel.readInt(),
    parcel.readInt(),
    parcel.readString(),
    parcel.readString()
  ) {
  }

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeInt(suspeitos)
    parcel.writeInt(confirmados)
    parcel.writeInt(descartados)
    parcel.writeInt(monitoramento)
    parcel.writeInt(curados)
    parcel.writeInt(mortes)
    parcel.writeString(data)
    parcel.writeString(hora)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<Boletim> {
    override fun createFromParcel(parcel: Parcel): Boletim {
      return Boletim(parcel)
    }

    override fun newArray(size: Int): Array<Boletim?> {
      return arrayOfNulls(size)
    }
  }

  override fun toString(): String {
    return super.toString()
  }

}
