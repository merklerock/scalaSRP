package com.ssb.srp.common

import org.scalatest.FunSpec
import org.scalatest.BeforeAndAfter
import org.scalatest.matchers.ShouldMatchers

import com.merklerock.common.srp.ClientSRPParameter;
import com.merklerock.common.srp.SRPParameter;
import com.merklerock.common.srp.ServerSRPParameter;
import com.merklerock.common.srp.Util._
import com.merklerock.common.srp._
	  
class SRPSpec extends FunSpec with ShouldMatchers with BeforeAndAfter{
  object srp extends SRPParameter
  val nhex = srp.N.toByteArray.toHexString
  val ghex = Array(srp.g.toByte).toHexString
  val I = "user"

  def testValidity(srpserver:ServerSRPParameter, 
		  			srpclient:ClientSRPParameter,
		  			password:String, shouldFail:Boolean = false) = {
    val (sVal,xVal) =  srpserver.x(password)
	val vVal = srpserver.v(xVal)
	val a = srpclient.a
    val A = srpclient.A(a)
    val b = srpserver.b
    val B = srpserver.B(vVal, b)
    val u = H(A,B)
    val x = srpclient.x(sVal, password.getBytes())
    val S = srpclient.S(BigInt(x), BigInt(B), BigInt(a), BigInt(u))
    val K = H(S)
    val Sserver = srpserver.S(A,vVal,u,b)
    val Kserver = H(Sserver)
    
    if(shouldFail){
    	Sserver.toHexString should not equal (S.toHexString)
    	Kserver.toHexString should not equal (K.toHexString)
    }else{
        Sserver.toHexString should equal (S.toHexString)
    	Kserver.toHexString should equal (K.toHexString)
    }
    
  } 
  
  before {
  }

  after {
  }

  describe("ServerSRPParameter") {

    it("should have session key generated equal to session key generated by the " +
    		"ClientSRPParameter when " +
    		"N=d4c7f8a2b32c11b8fba9581ec4ba4f1b04215642ef7355e37c0fc0443ef756ea2c6b8eeb755a1c723027663caa265ef785b8ff6a9b35227a52d86633dbdfca43" +
    		" and Password=password") {
    	object srpserver extends ServerSRPParameter{
    	  override def N = BigInt("d4c7f8a2b32c11b8fba9581ec4ba4f1b04215642ef7355e37c0fc0443ef756ea2c6b8eeb755a1c723027663caa265ef785b8ff6a9b35227a52d86633dbdfca43", 16)
    	}
    	object srpclient extends ClientSRPParameter{
    	  override def N = BigInt("d4c7f8a2b32c11b8fba9581ec4ba4f1b04215642ef7355e37c0fc0443ef756ea2c6b8eeb755a1c723027663caa265ef785b8ff6a9b35227a52d86633dbdfca43", 16)
    	}
    	testValidity(srpserver, srpclient, "password")
    }

    it("should have session key generated equal to session key generated by the " +
    		"ClientSRPParameter when " +
    		"N=115b8b692e0e045692cf280b436735c77a5a9e8a9e7ed56c965f87db5b2a2ece3" +
    		" and Password=hellayapassword") {
        object srpserver extends ServerSRPParameter{
    	  override def N = BigInt("115b8b692e0e045692cf280b436735c77a5a9e8a9e7ed56c965f87db5b2a2ece3", 16)
    	}
    	object srpclient extends ClientSRPParameter{
    	  override def N = BigInt("115b8b692e0e045692cf280b436735c77a5a9e8a9e7ed56c965f87db5b2a2ece3", 16)
    	}
    	testValidity(srpserver, srpclient, "hellayapassword")
    }
    
    it("should have session key generated equal to session key generated by the " +
    		"ClientSRPParameter when " +
    		"N=c94d67eb5b1a2346e8ab422fc6a0edaeda8c7f894c9eeec42f9ed250fd7f0046e5af2cf73d6b2fa26bb08033da4de322e144e7a8e9b12a0e4637f6371f34a2071c4b3836cbeeab15034460faa7adf483" +
    		" and Password=640bithere") {
        object srpserver extends ServerSRPParameter{
    	  override def N = BigInt("c94d67eb5b1a2346e8ab422fc6a0edaeda8c7f894c9eeec42f9ed250fd7f0046e5af2cf73d6b2fa26bb08033da4de322e144e7a8e9b12a0e4637f6371f34a2071c4b3836cbeeab15034460faa7adf483", 16)
    	}
    	object srpclient extends ClientSRPParameter{
    	  override def N = BigInt("c94d67eb5b1a2346e8ab422fc6a0edaeda8c7f894c9eeec42f9ed250fd7f0046e5af2cf73d6b2fa26bb08033da4de322e144e7a8e9b12a0e4637f6371f34a2071c4b3836cbeeab15034460faa7adf483", 16)
    	}
    	testValidity(srpserver, srpclient, "640bithere")
    }
    
    it("should have session key generated equal to session key generated by the " +
    		"ClientSRPParameter when " +
    		"N=b344c7c4f8c495031bb4e04ff8f84ee95008163940b9558276744d91f7cc9f402653be7147f00f576b93754bcddf71b636f2099e6fff90e79575f3d0de694aff737d9be9713cef8d837ada6380b1093e94b6a529a8c6c2be33e0867c60c3262b" +
    		" and Password=768bithere") {
        object srpserver extends ServerSRPParameter{
    	  override def N = BigInt("b344c7c4f8c495031bb4e04ff8f84ee95008163940b9558276744d91f7cc9f402653be7147f00f576b93754bcddf71b636f2099e6fff90e79575f3d0de694aff737d9be9713cef8d837ada6380b1093e94b6a529a8c6c2be33e0867c60c3262b", 16)
    	}
    	object srpclient extends ClientSRPParameter{
    	  override def N = BigInt("b344c7c4f8c495031bb4e04ff8f84ee95008163940b9558276744d91f7cc9f402653be7147f00f576b93754bcddf71b636f2099e6fff90e79575f3d0de694aff737d9be9713cef8d837ada6380b1093e94b6a529a8c6c2be33e0867c60c3262b", 16)
    	}
    	testValidity(srpserver, srpclient, "768bithere")
    }
    
    it("should have session key generated equal to session key generated by the " +
    		"ClientSRPParameter when " +
    		"N=eeaf0ab9adb38dd69c33f80afa8fc5e86072618775ff3c0b9ea2314c9c256576d674df7496ea81d3383b4813d692c6e0e0d5d8e250b98be48e495c1d6089dad15dc7d7b46154d6b6ce8ef4ad69b15d4982559b297bcf1885c529f566660e57ec68edbc3c05726cc02fd4cbf4976eaa9afd5138fe8376435b9fc61d2fc0eb06e3" +
    		" and Password=1024bithere") {
        object srpserver extends ServerSRPParameter{
    	  override def N = BigInt("eeaf0ab9adb38dd69c33f80afa8fc5e86072618775ff3c0b9ea2314c9c256576d674df7496ea81d3383b4813d692c6e0e0d5d8e250b98be48e495c1d6089dad15dc7d7b46154d6b6ce8ef4ad69b15d4982559b297bcf1885c529f566660e57ec68edbc3c05726cc02fd4cbf4976eaa9afd5138fe8376435b9fc61d2fc0eb06e3", 16)
    	}
    	object srpclient extends ClientSRPParameter{
    	  override def N = BigInt("eeaf0ab9adb38dd69c33f80afa8fc5e86072618775ff3c0b9ea2314c9c256576d674df7496ea81d3383b4813d692c6e0e0d5d8e250b98be48e495c1d6089dad15dc7d7b46154d6b6ce8ef4ad69b15d4982559b297bcf1885c529f566660e57ec68edbc3c05726cc02fd4cbf4976eaa9afd5138fe8376435b9fc61d2fc0eb06e3", 16)
    	}
    	testValidity(srpserver, srpclient, "1024bithere")
    }
    
    it("should have session key generated not equal to session key generated by the " +
    		"ClientSRPParameter when " +
    		"N's used are different" +
    		" and Password=1024bithere") {
        object srpserver extends ServerSRPParameter{
    	  override def N = BigInt("eeaf0ab9adb38dd69c33f80afa8fc5e86072618775ff3c0b9ea2314c9c256576d674df7496ea81d3383b4813d692c6e0e0d5d8e250b98be48e495c1d6089dad15dc7d7b46154d6b6ce8ef4ad69b15d4982559b297bcf1885c529f566660e57ec68edbc3c05726cc02fd4cbf4976eaa9afd5138fe8376435b9fc61d2fc0eb06e3", 16)
    	}
    	object srpclient extends ClientSRPParameter{
    	  override def N = BigInt("eeaf0ab9adb38dd69c33280afa8fc5e86072618775ff3c0b9ea2314c9c256576d674df7496ea81d3383b4813d692c6e0e0d5d8e250b98be48e495c1d6089dad15dc7d7b46154d6b6ce8ef4ad69b15d4982559b297bcf1885c529f566660e57ec68edbc3c05726cc02fd4cbf4976eaa9afd5138fe8376435b9fc61d2fc0eb06e3", 16)
    	}
    	testValidity(srpserver, srpclient, "1024bithere", true)
    }
  }
  
  describe("SRP Parameters"){
      object srp extends SRPParameter{
    	  override def k = BigInt("b7867f1299da8cc24ab93e08986ebc4d6a478ad0",16).toByteArray
      }
      
	  object srpserver extends ServerSRPParameter{
	      override def k = BigInt("b7867f1299da8cc24ab93e08986ebc4d6a478ad0",16).toByteArray
	      override def x(password: String) = (BigInt("25f28d47829b79ccc305",16).toByteArray,BigInt("d7ee10a91ade4e5bf1bca287a5d3e8dc9e2003e4",16).toByteArray)
	      override def b = BigInt("4d0936030b865bae1e400fb37503573300497bda35d675a5ad9dc321b74dc704",16).toByteArray
	  }
	  
	  object srpclient extends ClientSRPParameter{
	      override def k = BigInt("b7867f1299da8cc24ab93e08986ebc4d6a478ad0",16).toByteArray
	      override def x(S:Array[Byte], password:Array[Byte]) = BigInt("d7ee10a91ade4e5bf1bca287a5d3e8dc9e2003e4",16).toByteArray
	      override def a = BigInt("941682c0685941337a2d496818689f11332e02b8838f1a173e1e0119bd5f2d58",16).toByteArray
	  }
	  
		val (sVal,xVal) =  srpserver.x("password")
		val vVal = srpserver.v(xVal)
		val a = srpclient.a
	    val A = srpclient.A(a)
	    val b = srpserver.b
	    val B = srpserver.B(vVal, b)
	    val u = BigInt("12e725d344100dec8ba7a4bb8e23d7da34724708",16).toByteArray
	    val x = srpclient.x(sVal, "password".getBytes())
	    val S = srpclient.S(BigInt(x), BigInt(B), BigInt(a), BigInt(u))
	    val K = H(S)
	    val Sserver = srpserver.S(A,vVal,u,b)
	    val Kserver = H(Sserver)
    
	    it("should have v=256ae268a51c03d961ced668769b1a4e3c709f508268ac0cb98418f314fd869e9a99037d3b7f7305c16e3983741539fd5d8a6f0814c6b812046fdab11b3ce365"){
			(BigInt(vVal)) should equal (BigInt("256ae268a51c03d961ced668769b1a4e3c709f508268ac0cb98418f314fd869e9a99037d3b7f7305c16e3983741539fd5d8a6f0814c6b812046fdab11b3ce365",16))
		}
		
		it("should have A=6337a76f2cd349bdc1338d7188282b43daa8df1fe960e3edec491ba14b4ffa29d48eafd534592ee64f15f5fa427515a9920e079520a7ebbfe677c7ffc8881296"){
		    (BigInt(A)) should equal (BigInt("6337a76f2cd349bdc1338d7188282b43daa8df1fe960e3edec491ba14b4ffa29d48eafd534592ee64f15f5fa427515a9920e079520a7ebbfe677c7ffc8881296",16))
		}
		
		it("should have B=8423b5dc44740d7e96eacb381a05634977b6e8685404e555b78e373474b41f4e178efb1ae84a3723bef8737fea1885350bbba2236e1cabd8579a91d0670457cc"){
		    (BigInt(B)) should equal (BigInt("008423b5dc44740d7e96eacb381a05634977b6e8685404e555b78e373474b41f4e178efb1ae84a3723bef8737fea1885350bbba2236e1cabd8579a91d0670457cc",16))
		}
		
		it("should have server and client session key=79414e313178c559c21f97b59e62a67356544ebd6346a5cc77e4e1a87ba57b43223045430d312dfaaddc9de90e750998d3cd8b75d9c376c6891a5573a924175a"){
		    (BigInt(Sserver)) should equal (BigInt("79414e313178c559c21f97b59e62a67356544ebd6346a5cc77e4e1a87ba57b43223045430d312dfaaddc9de90e750998d3cd8b75d9c376c6891a5573a924175a",16))
		    (BigInt(S)) should equal (BigInt("79414e313178c559c21f97b59e62a67356544ebd6346a5cc77e4e1a87ba57b43223045430d312dfaaddc9de90e750998d3cd8b75d9c376c6891a5573a924175a",16))
		}
  }
  
}