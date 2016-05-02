package pt.webdetails.basic.plugin.dedicated;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.pentaho.platform.api.engine.IParameterProvider;
import org.pentaho.platform.api.repository2.unified.IUnifiedRepository;
import org.pentaho.platform.api.repository2.unified.RepositoryFile;
import org.pentaho.platform.api.repository2.unified.data.simple.SimpleRepositoryFileData;
import org.pentaho.platform.util.messages.LocaleHelper;
import pt.webdetails.basic.plugin.BasicPluginContentGenerator;
import pt.webdetails.basic.plugin.api.IBasicPluginSettings;

import java.io.InputStream;
import java.net.URLDecoder;

/**
 * Content generator that handles the new dedicated content type
 */
public class BasicPluginDedicatedContentGenerator extends BasicPluginContentGenerator {

  private String extension;
  private String mimeType;

  public BasicPluginDedicatedContentGenerator( IBasicPluginSettings settings, IUnifiedRepository repository ) {
    super( settings, repository );
  }

  public BasicPluginDedicatedContentGenerator( IBasicPluginSettings settings, IUnifiedRepository repository,
      String extension, String mimeType ) {
    super( settings, repository );
    setExtension( extension );
    setMimeType( mimeType );
  }

  @Override public void createContent() throws Exception {
    info( "createContent() called in " + ( isEditMode() ? "'edit'" : "'open'" ) + " mode" );

    IParameterProvider pathParams = parameterProviders.get( PATH_PARAMETER_ID );

    String urlEncodedFilePath;

    if( pathParams != null
        && !StringUtils.isEmpty( ( urlEncodedFilePath = pathParams.getStringParameter( PATH_PARAMETER_ID, null ) ) ) ) {

      String filePath = URLDecoder.decode( urlEncodedFilePath, LocaleHelper.UTF_8 );

      info( "createContent() called for file: " + filePath );

      if ( !getExtension().equalsIgnoreCase( FilenameUtils.getExtension( filePath ) ) ) {
        error( "this ContentGenerator will only handle files with '." + getExtension() + "' extension" );
        throw new UnsupportedOperationException( "Unsupported file extension: " + FilenameUtils.getExtension( filePath ) );
      }

      getResponse().setContentType( getMimeType() );
      getResponse().setHeader( "Cache-Control", "no-cache" );

      InputStream content = null;

      try {

        RepositoryFile file = getRepository().getFile( filePath );

        SimpleRepositoryFileData data = getRepository().getDataForRead( file.getId(), SimpleRepositoryFileData.class );

        writeOutAndFlush( getResponse().getOutputStream(), data.getInputStream() );

      } finally {
        IOUtils.closeQuietly( content );
      }
    }
  }

  public String getExtension() {
    return extension;
  }

  public void setExtension( String extension ) {
    this.extension = extension;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType( String mimeType ) {
    this.mimeType = mimeType;
  }
}
