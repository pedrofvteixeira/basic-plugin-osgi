package pt.webdetails.basic.plugin.dedicated;

import org.pentaho.platform.api.mimetype.IMimeType;
import org.pentaho.platform.api.repository2.unified.IUnifiedRepository;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.plugin.services.importer.RepositoryFileImportFileHandler;

import java.util.List;

public class BasicPluginImportHandler extends RepositoryFileImportFileHandler {

  public BasicPluginImportHandler( List<IMimeType> mimeTypes ) {
    super( mimeTypes );
  }

  @Override
  public void setRepository( IUnifiedRepository repository ) {
    super.setRepository( safeSetUnifiedRepository( repository ) );
  }

  protected IUnifiedRepository safeSetUnifiedRepository( IUnifiedRepository repository ) {

    try {

      if( repository != null && repository.toString() != null ){
        return repository;
      }

    } catch ( Throwable t ) {
      /* do nothing, use fallback */
    }

    // fallback
    return PentahoSystem.get( IUnifiedRepository.class );
  }
}
