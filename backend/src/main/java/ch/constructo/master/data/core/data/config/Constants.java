package ch.constructo.master.data.core.data.config;

public interface Constants {
  public static final String SIDAC_PERSISTENCE_UNIT_NAME = "sidac";
  public static final String MASTER_PERSISTENCE_UNIT_NAME = "master";


  /**
   * Constant <code>CH_QUALICASA_MASTER_EM="masterdataEM"</code>
   */
  public final static String CH_QUALICASA_MASTER_EM = "masterdataEM";
  /**
   * Constant <code>CH_QUALICASA_SIDAC_EM="sidacappEM"</code>
   */
  public final static String CH_QUALICASA_SIDAC_EM = "sidacappEM";

  /**
   * Constant <code>CH_QUALICASA_MASTER_DS="masterdataDS"</code>
   */
  public final static String CH_QUALICASA_MASTER_DS = "masterdataDS";
  /**
   * Constant <code>CH_QUALICASA_SIDAC_DS="sidacappDS"</code>
   */
  public final static String CH_QUALICASA_SIDAC_DS = "sidacappDS";


  /**
   * Constant <code>CH_QUALICASA_MASTER_DATA_TRX="masterDataTrxManager"</code>
   */
  public final static String CH_QUALICASA_MASTER_DATA_TRX = "masterDataTrxManager";
  /**
   * Constant <code>CH_QUALICASA_SIDAC_DATA_TRX="sidacDataTrxManager"</code>
   */
  public final static String CH_QUALICASA_SIDAC_DATA_TRX = "sidacDataTrxManager";


  /**
   * Constant <code>CH_QUALICASA_SIDACAPP_DATA="ch.qualicasa.sidacapp.data"</code>
   */
  public final static String CH_QUALICASA_MASTER_DATA = "ch.constructo.backend.core.data";
  /**
   * Constant <code>CH_QUALICASA_SIDACAPP_SERVICES="ch.qualicasa.sidacapp.services"</code>
   */
  public final static String CH_QUALICASA_MASTER_SERVICES = "ch.constructo.backend.services";


  /**
   * Constant <code>CH_QUALICASA_SIDACAPP_DATA="ch.qualicasa.sidacapp.data"</code>
   */
  public final static String CH_QUALICASA_SIDACAPP_DATA = "ch.qualicasa.sidacapp.data";
  /**
   * Constant <code>CH_QUALICASA_SIDACAPP_SERVICES="ch.qualicasa.sidacapp.services"</code>
   */
  public final static String CH_QUALICASA_SIDACAPP_SERVICES = "ch.qualicasa.sidacapp.services";

  /**
   * Constant <code>SMS_MESSAGING_CLIENTS_PACKAGE="ch.qualicasa.sidacapp.core.client.sms"</code>
   */
  public final static String SMS_MESSAGING_CLIENTS_PACKAGE = "ch.qualicasa.sidacapp.core.client.sms";
  /**
   * Constant <code>MAIL_MESSAGING_CLIENTS_PACKAGE="ch.qualicasa.sidacapp.core.client.mail"</code>
   */
  public final static String MAIL_MESSAGING_CLIENTS_PACKAGE = "ch.qualicasa.sidacapp.core.client.mail";
  /**
   * Constant <code>MESSAGING_CLIENTS_PACKAGE="ch.qualicasa.sidacapp.core.client"</code>
   */
  public final static String MESSAGING_CLIENTS_PACKAGE = "ch.qualicasa.sidacapp.core.client";
  /**
   * Constant <code>MARISMA_BASE_CONFIG_PACKAGE="com.marisma.common.base.config"</code>
   */
  public final static String MARISMA_BASE_CONFIG_PACKAGE = "com.marisma.common.base.config";
  /**
   * Constant <code>MARISMA_BASE_INFO_PACKAGE="com.marisma.common.base.info"</code>
   */
  public final static String MARISMA_BASE_INFO_PACKAGE = "com.marisma.common.base.info";

  /**
   * Constant <code>DOMAIN_SUBPACKAGE=".domain"</code>
   */
  public final static String DOMAIN_SUBPACKAGE = ".domain";

  /**
   * Constant <code>CH_QUALICASA_SIDACAPP_DOMAIN_PACKAGE="CH_QUALICASA_SIDACAPP_DATA + DOMAIN_SUBPACKAGE"</code>
   */
  public final static String CH_QUALICASA_SIDACAPP_DOMAIN_PACKAGE = CH_QUALICASA_SIDACAPP_DATA + DOMAIN_SUBPACKAGE;
  /**
   * Constant <code>CH_QUALICASA_SIDACAPP_REPOSITORY_PACKAGE="CH_QUALICASA_SIDACAPP_DATA + .repositor"{trunked}</code>
   */
  public final static String CH_QUALICASA_SIDACAPP_REPOSITORY_PACKAGE = CH_QUALICASA_SIDACAPP_DATA + ".repository";


  /**
   * Constant <code>CH_QUALICASA_SIDACAPP_DOMAIN_PACKAGE="CH_QUALICASA_SIDACAPP_DATA + DOMAIN_SUBPACKAGE"</code>
   */
  public final static String CH_QUALICASA_MASTER_DOMAIN_PACKAGE = CH_QUALICASA_MASTER_DATA + DOMAIN_SUBPACKAGE;
  /**
   * Constant <code>CH_QUALICASA_MASTER_REPOSITORY_PACKAGE="CH_QUALICASA_MASTER_DATA + .repository"</code>
   */
  public final static String CH_QUALICASA_MASTER_REPOSITORY_PACKAGE = CH_QUALICASA_MASTER_DATA + ".repository";


  public final static class I18N {
    public final static String MESSAGES = "i18n/messages";
  }

}
