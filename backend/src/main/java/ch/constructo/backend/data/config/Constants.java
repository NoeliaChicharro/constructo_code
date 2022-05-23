package ch.constructo.backend.data.config;

public interface Constants {
  public static final String SIDAC_PERSISTENCE_UNIT_NAME = "sidac";
  public static final String MASTER_PERSISTENCE_UNIT_NAME = "master";


  /**
   * Constant <code>CH_QUALICASA_MASTER_EM="masterdataEM"</code>
   */
  String CH_CONSTRUCTO_MASTER_EM = "masterdataEM";

  /**
   * Constant <code>CH_QUALICASA_MASTER_DS="masterdataDS"</code>
   */
  String CH_QUALICASA_MASTER_DS = "masterdataDS";


  /**
   * Constant <code>CH_QUALICASA_MASTER_DATA_TRX="masterDataTrxManager"</code>
   */
  String CH_QUALICASA_MASTER_DATA_TRX = "masterDataTrxManager";


  /**
   * Constant <code>CH_QUALICASA_SIDACAPP_DATA="ch.qualicasa.sidacapp.data"</code>
   */
  String CH_QUALICASA_MASTER_DATA = "ch.constructo.backend.data";
  /**
   * Constant <code>CH_QUALICASA_SIDACAPP_SERVICES="ch.qualicasa.sidacapp.services"</code>
   */
  String CH_QUALICASA_MASTER_SERVICES = "ch.constructo.backend.services";

  /**
   * Constant <code>DOMAIN_SUBPACKAGE=".domain"</code>
   */
  String DOMAIN_SUBPACKAGE = ".entities";


  /**
   * Constant <code>CH_QUALICASA_SIDACAPP_DOMAIN_PACKAGE="CH_QUALICASA_SIDACAPP_DATA + DOMAIN_SUBPACKAGE"</code>
   */
  String CH_QUALICASA_MASTER_DOMAIN_PACKAGE = CH_QUALICASA_MASTER_DATA + DOMAIN_SUBPACKAGE;
  /**
   * Constant <code>CH_QUALICASA_MASTER_REPOSITORY_PACKAGE="CH_QUALICASA_MASTER_DATA + .repository"</code>
   */
  String CH_QUALICASA_MASTER_REPOSITORY_PACKAGE = CH_QUALICASA_MASTER_DATA + ".repositories";


  class I18N {
    String MESSAGES = "i18n/messages";
  }

}
